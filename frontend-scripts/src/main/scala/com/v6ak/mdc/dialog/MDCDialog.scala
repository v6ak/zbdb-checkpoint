package com.v6ak.mdc.dialog

import org.scalajs.dom.raw.{Element, EventTarget}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal("mdc.dialog.MDCDialog")
class MDCDialog(element: Element) extends js.Object{
  def show(): Unit = js.native
  def lastFocusedTarget: EventTarget = js.native
  def lastFocusedTarget_=(value: EventTarget): Unit = js.native

}
