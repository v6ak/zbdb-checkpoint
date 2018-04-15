package com.v6ak.util

import scala.language.dynamics
import scala.scalajs.js
import scala.scalajs.js.Dictionary

object DynDict extends Dynamic {
  def applyDynamicNamed[A](method: String)(tuples: (String, A)*) = js.Dictionary(tuples: _*)
  def applyDynamic[T](method: String)(): Dictionary[T] = js.Dictionary.empty[T]
}
