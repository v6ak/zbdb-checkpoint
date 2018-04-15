package com.v6ak.mobileDetect

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal
@js.native
class MobileDetect(userAgent: String) extends js.Object {
  def is(str: String): Boolean = js.native
}

object MobileDetect{
  lazy val globalMobileDetection = new MobileDetect(dom.window.navigator.userAgent)
}
