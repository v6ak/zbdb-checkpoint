package com.v6ak.udash.mdc

import scalatags.JsDom.all._
import org.scalajs.dom.html.{Div, Input}

object MDCTextFieldComponent {
  def apply(inputElement: Input, labelContent: Frag, oterElements: Seq[Frag] = Seq()): Div = MDCTextInputWrapper(div(cls := "mdc-text-field")(
    inputElement,
    label(cls := "mdc-floating-label", `for` := inputElement.id)(labelContent),
    oterElements,
    div(cls := "mdc-line-ripple")
  ).render)
}
