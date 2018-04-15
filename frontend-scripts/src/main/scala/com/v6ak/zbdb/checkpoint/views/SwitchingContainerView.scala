package com.v6ak.zbdb.checkpoint.views

import io.udash.ContainerView
import io.udash.core.View
import org.scalajs.dom.Element
import scalatags.JsDom.all._
import scalatags.generic.Modifier

abstract class SwitchingContainerView extends ContainerView {

  protected def content: Modifier[Element]

  private val contentWrapper = div(content).render
  private val childrenWrapper = div(childViewContainer).render

  override final def getTemplate: Modifier[Element] = div(
    contentWrapper,
    childrenWrapper
  )

  override def renderChild(view: Option[View]): Unit = {
    contentWrapper.style.display = "none"
    childrenWrapper.style.display = "none"
    super.renderChild(view)
    contentWrapper.style.display = if(view.isEmpty) "block" else "none"
    childrenWrapper.style.display = if(view.isDefined) "block" else "none"
    view.collect{case x: ListenerView => x}.foreach{
      _.onShow()
    }
  }
}
