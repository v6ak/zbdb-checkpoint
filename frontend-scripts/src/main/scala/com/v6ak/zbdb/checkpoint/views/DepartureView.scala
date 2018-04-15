package com.v6ak.zbdb.checkpoint.views

import com.v6ak.udash.mdc.MDCTextFieldComponent
import com.v6ak.zbdb.checkpoint.Templates.tags._
import com.v6ak.zbdb.checkpoint.{ApplicationContext, ParticipantsSelectionComponent}
import org.scalajs.dom.html.Input
import scalatags.JsDom.all._

class DepartureView(implicit protected val applicationContext: ApplicationContext) extends TableFormView{

  override def pageTitle: String = "Odchod ze stanoviště"

  private val timeField = input(tpe := "time", cls := "mdc-text-field__input", id := "departure-time").render

  private val participantSelection = new ParticipantsSelectionComponent("departure-participant-selection")

  override def primaryField: Input = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq[Frag](
    section(
      MDCTextFieldComponent(
        timeField,
        "Čas odchodu (nepovinné)",
        Seq("(prázdné políčko se vyplní po odeslání)")
      ),
    ),
    participantSelection.content
  )

}
