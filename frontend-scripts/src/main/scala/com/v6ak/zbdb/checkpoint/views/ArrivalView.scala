package com.v6ak.zbdb.checkpoint.views

import com.v6ak.udash.mdc.MDCTextFieldComponent
import com.v6ak.zbdb.checkpoint.legacy.Templates.tags.section
import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.components.ParticipantsSelectionComponent
import org.scalajs.dom.html.Input
import scalatags.JsDom.all._

class ArrivalView(implicit protected val applicationContext: ApplicationContext) extends TableFormView{

  private def formattedCurrentTime = {
    val now = new scala.scalajs.js.Date()
    f"${now.getHours()}:${now.getMinutes()}%02d"
  }

  private val timeField = input(tpe := "time", cls := "mdc-text-field__input", id := "arrival-time", value:=formattedCurrentTime).render

  private val participantSelection = new ParticipantsSelectionComponent("arrival-participant-selection")

  override def primaryField: Input = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq[Frag](
    section(
      MDCTextFieldComponent(
        timeField,
        "Čas příchodu",
        Seq("(předvyplněno automaticky)")
      )
    ),
    participantSelection.content
  )

  override def pageTitle: String = "Příchod na stanoviště"
}
