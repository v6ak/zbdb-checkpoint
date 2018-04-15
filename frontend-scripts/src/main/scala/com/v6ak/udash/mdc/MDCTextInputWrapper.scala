package com.v6ak.udash.mdc

import com.v6ak.mdc.textField.MDCTextField
import org.scalajs.dom.Element

object MDCTextInputWrapper {
  def apply(el: Element): el.type = {
    // TODO: consider:
    //    val mobileDetect = new MobileDetect(dom.window.navigator.userAgent)
    //    if (mobileDetect.is("Gecko") && mobileDetect.is("AndroidOS")) {
    //      // workaround for time fields
    //      root.querySelectorAll("""input[type="time"].mdc-text-field__input""").foreach {
    //        _.parentNode.asInstanceOf[Element].querySelector("label").classList.add("mdc-floating-label--float-above")
    //      }
    //    }
    MDCTextField.attachTo(el)
    el
  }
}
