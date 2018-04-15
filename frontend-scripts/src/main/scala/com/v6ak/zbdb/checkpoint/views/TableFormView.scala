package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.legacy.Templates._
import com.v6ak.zbdb.checkpoint.legacy.Templates.tags._
import com.v6ak.zbdb.checkpoint.routing.TableState
import io.udash.core.ContainerView
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLInputElement
import scalatags.JsDom.all._
import scalatags.generic

abstract class TableFormView extends ContainerView with ListenerView{

  protected implicit def applicationContext: ApplicationContext

  def pageTitle: String

  def formItems: Seq[Frag]

  def primaryField: HTMLInputElement

  override def getTemplate: generic.Modifier[Element] = Seq(
    dialogHeader(pageTitle, TableState),
    main(role:="main", cls:="mdc-toolbar-fixed-adjust")(
      formItems
    )
  ).render

  override def onShow(): Unit = {
    primaryField.focus()
  }

}
