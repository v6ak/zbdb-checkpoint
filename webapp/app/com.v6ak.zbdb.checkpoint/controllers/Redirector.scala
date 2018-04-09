package com.v6ak.zbdb.checkpoint.controllers

import javax.inject._
import play.api.mvc._

@Singleton
class Redirector @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def redirect(target: String) = Action{
    Redirect(target)
  }

}
