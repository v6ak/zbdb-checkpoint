package com.v6ak.zbdb.checkpoint.components

import com.v6ak.udash.mdc.MDCTextInputWrapper
import com.v6ak.zbdb.checkpoint.legacy.Templates.participantSelectionListItemStructure
import com.v6ak.zbdb.checkpoint.data.DataStub
import com.v6ak.zbdb.checkpoint.legacy.Templates
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.html.{Input, LI, UList}
import org.scalajs.dom.raw.Node
import scalatags.JsDom.all._

import scala.collection.mutable
import scala.util.Try

class ParticipantsSelectionComponent(idPrefix: String) {

  var lastAdditionComponent: AdditionSubcomponent = _

  val content: UList = ul(cls:="mdc-list mdc-list--two-line participant-list")().render

  private val subcomponents = mutable.Map[AdditionSubcomponent, Node]()

  {
    addAdditionComponent()
  }

  def addAdditionComponent(): Unit ={
    val subcomponent = new AdditionSubcomponent()
    lastAdditionComponent = subcomponent
    val result = content.appendChild(subcomponent.content)
    subcomponents(subcomponent) = result
  }

  def removeAdditionComponent(subcomponent: AdditionSubcomponent): Unit ={
    content.removeChild(subcomponents(subcomponent))
    subcomponents.remove(subcomponent)
  }

  class AdditionSubcomponent(){
    private val nameSpan = span().render
    private val stateSpan = span().render
    val field: Input = input(
      tpe := "number",
      cls := "mdc-text-field__input",
      size := "4",
      onkeyup := handleKeyUp _,
      onkeypress := detectChange _,
      onchange := detectChange _,
      onblur := handleBlur _
    ).render

    def isLast: Boolean = this == lastAdditionComponent

    private def handleBlur(): Unit ={
      if((!isLast) && (field.value == "")){
        removeAdditionComponent(this)
      }else{
        detectChange()
      }
    }

    private def handleKeyUp(e: KeyboardEvent): Unit ={
      detectChange()
      if(e.keyCode == 13 || e.keyCode == 10){
        lastAdditionComponent.field.focus()
      }
    }

    private def detectChange(): Unit ={
      val valueStr = field.value
      if(isLast && valueStr != ""){
        addAdditionComponent()
      }
      val (name, note) = Try{valueStr.toInt}.fold(_ => ("Není číslo!", ""), id => DataStub.byId(id).fold(("Nenalezen!", ""))(participant => (participant.name, Templates.Descriptions(participant.state))))
      nameSpan.textContent = name
      stateSpan.textContent = note
    }
    def content: LI = {
      participantSelectionListItemStructure(
        additionalClasses = "mdc-list-item--add",
        deleteHandlerOption = None,
        icon = "add",
        namePlaceholder = MDCTextInputWrapper(div(cls := "mdc-text-field")(
          field,
          nameSpan,
          div(cls := "mdc-line-ripple")
        ).render),
        statusPlaceholder = stateSpan
      ).render
    }
  }

}
