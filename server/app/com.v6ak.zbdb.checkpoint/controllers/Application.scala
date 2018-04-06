package com.v6ak.zbdb.checkpoint.controllers

import javax.inject._
import play.api.libs.json.JsString
import play.api.mvc._
import play.twirl.api.Txt

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def clientOptFake() = Action {
    val scriptsHtml = scalajs.html.scripts("client", controllers.routes.Assets.versioned(_).toString, name => getClass.getResource(s"/public/$name") != null)
    val scriptsLoadJs = "document.write("+JsString(scriptsHtml.toString())+");"
    Ok(Txt(scriptsLoadJs)).as("application/javascript")
  }

}
