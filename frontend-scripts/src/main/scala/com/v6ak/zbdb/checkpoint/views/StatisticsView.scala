package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.legacy.Templates._
import com.v6ak.zbdb.checkpoint.data.DataStub
import org.scalajs.dom.Element
import scalatags.JsDom.all._
import scalatags.generic

class StatisticsView(implicit applicationContext: ApplicationContext) extends SwitchingContainerView {
  override def content: generic.Modifier[Element] = div(
    appHeader("Statistiky pro " + applicationContext.title),
    appContent(
      ul(cls := "mdc-list mdc-list--two-line", id := "stats"){
        val stats = DataStub.participants.groupBy(_.state).mapValues(_.size).withDefaultValue(0)
        assert((stats.keySet -- Descriptions.keySet) == Set())
        for ((state, description) <- Descriptions.toSeq) yield statsItem(description, stats(state))
      }
    )
  )
}