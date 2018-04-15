package com.v6ak.zbdb.checkpoint

import org.scalajs.dom


object Main{

  def main(args: Array[String]): Unit = {
    val appRoot = dom.document.getElementById("application")
    val context = ApplicationContext(1, "Pokusné stanoviště", 1984)
    context.application.run(appRoot)
    dom.document.getElementById("welcome").classList.add("hidden")
  }

}
