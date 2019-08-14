package com.v6ak.zbdb.checkpoint

import org.scalajs.dom


object Main{

  def main(args: Array[String]): Unit = {
    val appRoot = dom.document.getElementById("application")
    val context = ApplicationContext()
    context.application.run(appRoot)
    dom.document.getElementById("welcome").classList.add("hidden")
  }

}
