package com.v6ak.zbdb.checkpoint
import com.v6ak.zbdb.checkpoint.Templates.tags.section
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement
import scalatags.JsDom.all._

class ArrivalPage() extends TableFormPage{

  private def formattedCurrentTime = {
    val now = new scala.scalajs.js.Date()
    val arrivalTime = dom.document.getElementById("arrival-time").asInstanceOf[HTMLInputElement]
    s"${now.getHours()}:${now.getMinutes()}"
  }

  private val timeField = input(tpe := "time", cls := "mdc-text-field__input", id := "arrival-time", value:=formattedCurrentTime).render

  private val participantSelection = new ParticipantsSelectionComponent("arrival-participant-selection")

  override def primaryField = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq[Frag](
    section(
      div(cls:="mdc-text-field")(
        timeField,
        label(cls:="mdc-floating-label mdc-floating-label--float-above", `for`:="departure-time")("Čas příchodu"),
        div(cls:="mdc-line-ripple"),
      )
    ),
    participantSelection.content
  )

  override def pageTitle: String = "Příchod na stanoviště"
}
