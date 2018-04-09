package com.v6ak.zbdb.checkpoint

import com.v6ak.zbdb.checkpoint.Templates.tags._
import org.scalajs.dom.html.Input
import scalatags.JsDom.all._

class DeparturePage() extends TableFormPage{

  override def pageTitle: String = "Odchod ze stanoviště"

  private val timeField = input(tpe := "time", cls := "mdc-text-field__input", id := "departure-time").render

  private val participantSelection = new ParticipantsSelectionComponent("departure-participant-selection")

  override def primaryField: Input = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq[Frag](
    section(
      div(cls:="mdc-text-field")(
        timeField,
        "(prázdné políčko se vyplní po odeslání)",
        label(cls:="mdc-floating-label", `for`:="departure-time")("Čas odchodu (nepovinné)"),
        div(cls:="mdc-line-ripple"),
      )
    ),
    participantSelection.content
  )

}
