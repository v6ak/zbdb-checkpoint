package com.v6ak.udash.mdc

import com.v6ak.mdc.textField.MDCTextField
import com.v6ak.mobileDetect.MobileDetect.globalMobileDetection
import org.scalajs.dom.Element
import org.scalajs.dom.ext._

object MDCTextInputWrapper {
  def apply(el: Element): el.type = {
    if (globalMobileDetection.is("Firefox") && globalMobileDetection.is("AndroidOS")) {
      // workaround for time fields
      el.querySelectorAll("""input[type="time"].mdc-text-field__input""").foreach {
        _.parentNode.asInstanceOf[Element].querySelector("label").classList.add("mdc-floating-label--float-above")
      }
    }
    MDCTextField.attachTo(el)
    el
  }
}
