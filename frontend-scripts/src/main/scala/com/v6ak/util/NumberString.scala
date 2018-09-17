package com.v6ak.util

import scala.util.Try

object NumberString {
  def unapply(s: String): Option[Int] = if(s.startsWith("0")) None else Try{Integer.parseInt(s)}.toOption
}
