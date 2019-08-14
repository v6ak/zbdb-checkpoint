package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import io.udash.ContainerView
import org.scalajs.dom.Element
import scalatags.JsDom.all._
import scalatags.generic.Modifier

class RootView(implicit applicationContext: ApplicationContext) extends ContainerView {
  override def getTemplate: Modifier[Element] = Seq[Frag](
    childViewContainer
  )
}
