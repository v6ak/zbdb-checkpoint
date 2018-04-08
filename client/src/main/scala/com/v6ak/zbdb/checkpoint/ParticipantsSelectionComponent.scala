package com.v6ak.zbdb.checkpoint

import com.v6ak.zbdb.checkpoint.Templates.{participantSelectionAddForm, participantSelectionListItemStructure}
import com.v6ak.zbdb.checkpoint.data.DataStub
import org.scalajs.dom
import org.scalajs.dom.html.LI
import org.scalajs.dom.raw.Node
import scalatags.JsDom
import scalatags.JsDom.all._

import scala.collection.mutable
import scala.util.Try

class ParticipantsSelectionComponent(idPrefix: String) {

  var lastAdditionComponent = new AdditionSubcomponent()

  val content = ul(cls:="mdc-list mdc-list--two-line participant-list")(lastAdditionComponent.content).render

  private val subcomponents = mutable.Map[AdditionSubcomponent, Node]()

  def addAdditionComponent(): Unit ={
    val subcomponent = new AdditionSubcomponent()
    lastAdditionComponent = subcomponent
    val result = content.appendChild(subcomponent.content)
    subcomponents(subcomponent) = result
  }

  def removeAdditionComponent(subcomponent: AdditionSubcomponent): Unit ={
    dom.console.log(content.childNodes.length, subcomponent.content)
    content.removeChild(subcomponents(subcomponent))
    subcomponents.remove(subcomponent)
  }

  class AdditionSubcomponent(){
    private val nameSpan = span().render
    private val stateSpan = span().render
    private val field = input(
      tpe := "number",
      cls := "mdc-text-field__input",
      //id := idPrefix + "-new-item",
      size := "4",
      onkeyup := detectChange _,
      onkeypress := detectChange _,
      onchange := detectChange _,
      onblur := handleBlur _
    ).render

    def isLast = this == lastAdditionComponent

    private def handleBlur(): Unit ={
      if((!isLast) && (field.value == "")){
        removeAdditionComponent(this)
      }else{
        detectChange()
      }
    }

    private def detectChange(): Unit ={
      val valueStr = field.value
      if(isLast && valueStr != ""){
        addAdditionComponent()
      }
      dom.console.log(valueStr)
      val (name, note) = Try{valueStr.toInt}.fold(_ => ("Není číslo!", ""), id => DataStub.byId(id).fold(("Nenalezen!", ""))(participant => (participant.name, Templates.Descriptions(participant.state))))
      nameSpan.textContent = name
      stateSpan.textContent = note
    }
    def content: LI = {
      participantSelectionListItemStructure(
        additionalClasses = "mdc-list-item--add",
        deleteHandlerOption = None,
        icon = "add",
        namePlaceholder = div(cls := "mdc-text-field")(
          field,
          nameSpan,
          div(cls := "mdc-line-ripple")
        ),
        statusPlaceholder = stateSpan
      ).render
    }
  }

}
