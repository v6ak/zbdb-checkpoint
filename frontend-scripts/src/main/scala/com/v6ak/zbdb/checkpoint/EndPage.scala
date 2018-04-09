package com.v6ak.zbdb.checkpoint
import com.v6ak.zbdb.checkpoint.Templates.tags._
import scalatags.JsDom.all._


class EndPage() extends TableFormPage{
  override def pageTitle: String = "Ukončení účasti v pochodu"

  private val participantSelection = new ParticipantsSelectionComponent("end-participant-selection")

  override def primaryField = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq(
    participantSelection.content,
    section(
      Templates.radio("end-type", "arrived", "Účastník je na stanovišti"),
      Templates.radio("end-type", "will-not-arrive", "Účastník oznámil, že nepřijde"),
    )

  )
}
