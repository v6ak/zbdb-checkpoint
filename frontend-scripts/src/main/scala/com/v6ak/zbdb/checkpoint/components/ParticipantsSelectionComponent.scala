package com.v6ak.zbdb.checkpoint.components

import com.v6ak.udash.mdc.MDCTextInputWrapper
import com.v6ak.util.NumberString
import com.v6ak.zbdb.checkpoint.data.{DataStub, Participant}
import com.v6ak.zbdb.checkpoint.legacy.Templates
import com.v6ak.zbdb.checkpoint.legacy.Templates.participantSelectionListItemStructure
import io.udash._
import io.udash.bindings.NumberInput
import io.udash.properties.single.{Property, ReadableProperty}
import org.scalajs.dom
import org.scalajs.dom.html.{Input, LI, UList}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{Event, KeyboardEvent}
import scalatags.JsDom.all._

import scala.collection.mutable

abstract sealed class Result
object Result{
  final case object Empty extends Result
  final case object NotFound extends Result
  final case object InvalidNumber extends Result
  abstract sealed class Found extends Result { def participant: Participant }
  final case class Duplicate(participant: Participant) extends Found
  final case class Correct(participant: Participant) extends Found
}

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
    val rawFieldValue = Property("")
    val rawValue = Property("")
    rawFieldValue.listen(rawValue.set(_)) // one-way proxy that prevents propagating back on invalid input…
    val expValue = Property("")
    val result: ReadableProperty[Result] = rawValue.transform {
      case "" if !field.validity.valid => Result.InvalidNumber
      case "" => Result.Empty
      case NumberString(partId) => DataStub.byId(partId).fold[Result](Result.NotFound)(Result.Correct.apply _)
      case other => Result.InvalidNumber
    }
    val expResult: ReadableProperty[Result] = expValue.transform {
      case "" => Result.Empty
      case NumberString(partId) => DataStub.byId(partId).fold[Result](Result.NotFound)(Result.Correct.apply _)
      case other => Result.InvalidNumber
    }
    val field: Input = NumberInput.debounced(
      rawFieldValue,
      cls := "mdc-text-field__input",
      size := "4",
      onkeyup :+= handleKeyUp _,
      onblur :+= handleBlur _,
      // a hack for invalid input. TODO: consider creating an issue for Udash.
      oninput :+= ((_: Event) => {if(!field.validity.valid){rawValue.set("-")}})
    ).render

    def isLast: Boolean = this == lastAdditionComponent

    private def handleBlur(ev: Event): Unit ={
      if((!isLast) && (result.get == Result.Empty)){
        removeAdditionComponent(this)
      }
    }

    private def handleKeyUp(e: KeyboardEvent): Unit ={
      if(e.keyCode == 13 || e.keyCode == 10){
        lastAdditionComponent.field.focus() // TODO: switch to next component instead
      }
    }

    result.listen {result =>
      if(isLast && result != Result.Empty){ // After one addition, isLast will be false, so it will not retrigger…
        addAdditionComponent()
      }
    }

    def showResult(showCorrect: Participant => String): Result => String = {
      case Result.Empty => ""
      case Result.NotFound => "Nenalezen!"
      case Result.InvalidNumber => "Není číslo!"
      case Result.Duplicate(_) => "Tohoto účastníka vidím dvojmo."
      case Result.Correct(p) => showCorrect(p)
    }

    def content: LI = {
      participantSelectionListItemStructure(
        additionalClasses = "mdc-list-item--add",
        deleteHandlerOption = None,
        icon = "add",
        namePlaceholder = Seq(
          MDCTextInputWrapper(div(cls := "mdc-text-field")(
            field,
            div(cls := "mdc-line-ripple")
          ).render): Frag,
          div(cls := "name-autocomplete")(bind(result.transform( x => showResult(p => p.name)(x))))
        ).render,
        statusPlaceholder = bind(result.transform(showResult(p => Templates.Descriptions(p.state))))
      ).render
    }
  }

}
