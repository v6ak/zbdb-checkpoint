val material = "org.webjars.npm" % "material-components-web" % "0.34.1"

lazy val webapp = (project in file("webapp")).settings(commonSettings).settings(
  scalaJSProjects := Seq(frontendScripts),
  pipelineStages in Assets := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.1.1",
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
  ),
  jsDependencies ++= Seq(
    material / "dist/material-components-web.min.js"
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
