package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import io.udash.ContainerView
import org.scalajs.dom.Element
import scalatags.generic.Modifier
import scalatags.JsDom.all._
import scalatags.{JsDom, generic}
import com.v6ak.zbdb.checkpoint.Util.linkTo
import com.v6ak.zbdb.checkpoint.routing.TableState
//import com.definitelyscala.googlepicker.google.picker.{Feature, PickerBuilder}

class SelectSheetView (implicit applicationContext: ApplicationContext) extends ContainerView {
  override def getTemplate: Modifier[Element] = Seq(
    div("hello world!"),
    a(href := linkTo(TableState("asdf")), cls:= "btn btn-primary")("exp")
  )
}
