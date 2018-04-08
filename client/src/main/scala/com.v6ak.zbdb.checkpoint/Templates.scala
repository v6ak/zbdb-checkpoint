package com.v6ak.zbdb.checkpoint

import com.v6ak.zbdb.checkpoint.data.{DataStub, Participant, State}
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Div, LI}
import org.scalajs.dom.raw.{HTMLElement, Node}
import scalatags.JsDom
import scalatags.JsDom.all._

import scala.collection.immutable.ListMap
import scala.util.Random

object Templates {
  def activePage(identifier: String, node: Node): Node = div(cls:="subpage", id:=identifier)(node).render

  object tags {
    val section = typedTag[HTMLElement]("section")
    val main = typedTag[HTMLElement]("main")
  }
  import tags._

  def dialogHeader(title: String, backLink: String) = header(cls:="mdc-toolbar mdc-toolbar--fixed")(
    div(cls:="mdc-toolbar__row")(
      section(cls:="mdc-toolbar__section mdc-toolbar__section--align-start")(
        a(href:=backLink, cls:="material-icons mdc-toolbar__menu-icon mdc-ripple-surface")("clear"),
        span(cls:="mdc-toolbar__title")(title)
      ),
      section(cls:="mdc-toolbar__section mdc-toolbar__section--align-end", role:="toolbar")(
        button(cls:="material-icons mdc-toolbar__icon")("done")
      )
    )
  )

  val Descriptions = ListMap[State, String](
    State.Unknown -> "Není zaznamenán/zaznamenána na předchozím stanovišti",
    State.OnWay -> "Na cestě sem (z předchozího stanoviště)",
    State.Arrived -> "Na tomto stanovišti",
    State.Departed -> "Odešel/odešla z tohoto stanoviště",
    State.GivenUpThere -> "Skončil/skončila na tomto stanovišti",
    State.GivenUpBefore -> "Skončil/skončila před tímto stanovištěm",
  )

  def createParticipantsListPrototype(): Frag = ul(cls:="mdc-list mdc-list--two-line participant-list")(
    Random.shuffle(DataStub.participants).take(5).map(participantSelectionListItem(_, ()=>(), "add")) ++ Seq(participantSelectionAddForm())
  )

  def statsItem(description: String, count: Int): LI = li(cls:="mdc-list-item")(
    span(cls:="mdc-list-item__text")(
      description,
      span(cls:="mdc-list-item__secondary-text")(
        count
      ),
    )
  ).render

  def participantTableItem(participant: Participant, dirty: Boolean): JsDom.TypedTag[LI] = participantSelectionListItemStructure(
    deleteHandlerOption = None,
    icon = if(dirty) "sync_problem" else "",
    namePlaceholder = s"${participant.id}: ${participant.name}",
    statusPlaceholder = participant.state match {
      //case State.Arrived => s"Na stanovišti, přišel/přišla v ${participant.arrivalTime}"
      //case State.Departed => s"Pryč, příchod v ${participant.arrivalTime}, odchod v ${participant.departureTime}"
      //case State.GivenUpThere => s"Vzdal/vzdala to, příchod v ${participant.arrivalTime}"
      case state => Descriptions(state)
    }
  )

  def participantSelectionListItem(participant: Participant, deleteHandler: () => Unit, icon: String): JsDom.TypedTag[LI] = participantSelectionListItemStructure(
    deleteHandlerOption = Some(deleteHandler),
    icon = icon,
    namePlaceholder = s"${participant.id}: ${participant.name}",
    statusPlaceholder = Descriptions(participant.state)
  )

  def participantSelectionAddForm(): JsDom.TypedTag[LI] = participantSelectionListItemStructure(
    deleteHandlerOption = None,
    icon = "add",
    namePlaceholder = div(cls:="mdc-text-field")(
      input(tpe:="text", cls:="mdc-text-field__input", id:="new-item", size:="4"),
      div(cls:="mdc-line-ripple")
    ),
    statusPlaceholder = span().render
  )

  def participantSelectionListItemStructure(deleteHandlerOption: Option[() => Unit], icon: String, namePlaceholder: Frag, statusPlaceholder: Frag, additionalClasses: String = "") = {
    li(cls:="mdc-list-item "+additionalClasses)(
      span(cls:="mdc-list-item__graphic", role:="presentation")(
        i(cls:="material-icons")(icon)
      ),
      span(cls:="mdc-list-item__text")(
          namePlaceholder,
          span(cls:="mdc-list-item__secondary-text")(
            statusPlaceholder
          )
      ),
      deleteHandlerOption.fold[Frag]("")(deleteHandler =>
        a(href:="#", cls:="mdc-list-item__meta material-icons", title:="Smazat", onclick := ((e: Event) => {e.preventDefault(); deleteHandler()}))("delete")
      )
    )
  }

  def progressbar: JsDom.TypedTag[Div] = {
    div(role:="progressbar", cls:="mdc-linear-progress")(
      div(cls:="mdc-linear-progress__buffering-dots"),
      div(cls:="mdc-linear-progress__buffer"),
      div(cls:="mdc-linear-progress__bar mdc-linear-progress__primary-bar")(
        span(cls:="mdc-linear-progress__bar-inner")
      ),
      div(cls:="mdc-linear-progress__bar mdc-linear-progress__secondary-bar")(
        span(cls:="mdc-linear-progress__bar-inner")
      )
    )
  }


}
