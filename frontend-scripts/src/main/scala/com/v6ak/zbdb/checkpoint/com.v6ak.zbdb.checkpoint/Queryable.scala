package com.v6ak.zbdb.checkpoint

import org.scalajs.dom.raw.{NodeList, NodeSelector}

trait Queryable extends NodeSelector{

   def getElementsByClassName(elementName: String): NodeList

}
