val udashVersion = "0.6.1"

val material = "org.webjars.npm" % "material-components-web" % "0.38.2" //exclude("org.webjars.npm", "tabbable") //0.39.0 contains to many BC breaks that we are unable to handle right now: https://github.com/material-components/material-components-web/blob/master/CHANGELOG.md

val mobileDetect = "org.webjars.npm" % "mobile-detect" % "1.4.3"

lazy val webapp = (project in file("webapp")).settings(commonSettings).settings(
  scalaJSProjects := Seq(frontendScripts),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
    //"org.webjars.npm" % "material-design-icons" % "3.0.1", many files, so it causes no space left on device…
    mobileDetect,
    guice,
    material,
    specs2 % Test
  ),
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
  EclipseKeys.preTasks := Seq(compile in Compile)
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val frontendScripts = (project in file("frontend-scripts")).settings(commonSettings).settings(
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.4",
    "com.lihaoyi" %%% "scalatags" % "0.6.7",
    "io.udash" %%% "udash-core-frontend" % udashVersion,
  ),
  jsDependencies ++= Seq(
    material / "dist/material-components-web.js" minified("dist/material-components-web.min.js"),
    mobileDetect / "mobile-detect.min.js"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).settings(commonSettings)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.5",
  organization := "com.v6ak"
)

// loads the webapp project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project webapp" :: s}
