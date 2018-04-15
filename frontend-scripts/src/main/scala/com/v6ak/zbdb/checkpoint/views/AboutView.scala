package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.Templates._
import io.udash.ContainerView
import scalatags.JsDom.all._

class AboutView(implicit applicationContext: ApplicationContext) extends ContainerView {
  override def getTemplate: Modifier =  div(
    appHeader("O aplikaci"),
    appContent(
      div(
        a(href := "https://github.com/v6ak/zbdb-checkpoint", target := "_blank",
          i(cls := "material-icons mdc-list-item__graphic", attr("aria-hidden") := "true","code"),
          "Zdrojový kód"
        )
      )
    )
  )
}
