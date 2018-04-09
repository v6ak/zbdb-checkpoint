package com.v6ak.util

import scala.language.dynamics
import scala.scalajs.js

object DynDict extends Dynamic {
  def applyDynamicNamed[A](method: String)(tuples: (String, A)*) = js.Dictionary(tuples: _*)
}
