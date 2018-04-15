package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.legacy.Templates.tags._
import com.v6ak.zbdb.checkpoint.components.ParticipantsSelectionComponent
import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.legacy.Templates
import org.scalajs.dom.html.Input
import scalatags.JsDom.all._


class EndView(implicit protected val applicationContext: ApplicationContext) extends TableFormView{
  override def pageTitle: String = "Ukončení účasti v pochodu"

  private val participantSelection = new ParticipantsSelectionComponent("end-participant-selection")

  override def primaryField: Input = participantSelection.lastAdditionComponent.field

  override def formItems: Seq[Frag] = Seq(
    participantSelection.content,
    section(
      Templates.radio("end-type", "arrived", "Účastník je na stanovišti"),
      Templates.radio("end-type", "will-not-arrive", "Účastník oznámil, že nepřijde"),
    )
  )
}
