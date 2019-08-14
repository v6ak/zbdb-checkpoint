package com.v6ak.zbdb.checkpoint.views

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.legacy.Templates._
import com.v6ak.zbdb.checkpoint.Util._
import com.v6ak.zbdb.checkpoint.data.DataStub
import com.v6ak.zbdb.checkpoint.routing._
import scalatags.JsDom.all._

class TableView(sheetId: String)(implicit applicationContext: ApplicationContext) extends SwitchingContainerView {
  override def content: Modifier = div(
    appHeader(Todo.sheetTitle(sheetId)),
    appContent(
      div(cls := "mdc-card",
        div(cls := "mdc-card__actions",
          a(href := linkTo(ArrivalState(sheetId)), cls := "mdc-button mdc-button--raised")(
            i(cls := "material-icons mdc-button__icon", "add"), "příchod"
          ),
          a(href := linkTo(DepartureState(sheetId)), cls := "mdc-button mdc-button--raised")(
            i(cls := "material-icons mdc-button__icon", "remove"), "odchod"
          ),
          a(href := linkTo(EndState(sheetId)), cls := "mdc-button mdc-button--raised")(
            i(cls := "material-icons mdc-button__icon", "clear"), "konec"
          )
        )
      ),
      div(cls := "mdc-card mdc-card--alert","Vize: Tato tabulka by asi měla být editovatelná, řaditelná a měla by reflektovat zapsaná data."),
      div(cls := "mdc-select",
        select(cls := "mdc-select__native-control",
          option(value := "last-modified")("Podle poslední změny"),
          option(value := "id")("Podle startovního čísla")
        ),
        div(cls := "mdc-select__label mdc-select__label--float-above","Řazení"),
        div(cls := "mdc-select__bottom-line")
      ),
      ul(id := "participants", cls := "mdc-list mdc-list--dense mdc-list--two-line participant-list")(DataStub.participants.map(participant =>
        participantTableItem(participant, dirty = math.random() > 0.9).render)
      )
    )
  )
}