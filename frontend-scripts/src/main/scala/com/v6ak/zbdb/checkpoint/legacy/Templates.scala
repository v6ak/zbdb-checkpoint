package com.v6ak.zbdb.checkpoint.legacy

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.Util._
import com.v6ak.zbdb.checkpoint.data.{Participant, State}
import com.v6ak.zbdb.checkpoint.routing.RoutingState
import org.scalajs.dom.Event
import org.scalajs.dom.html.{Div, Element, LI}
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom
import scalatags.JsDom.all._

import scala.collection.immutable.ListMap

object Templates {
  def radio(idPrefix: String, option: String, description: Frag): Frag = {
    val radioId = s"$idPrefix-$option"
    div(cls := "mdc-form-field")(
      div(cls := "mdc-radio")(
        input(cls := "mdc-radio__native-control", tpe := "radio", id := radioId, name := idPrefix),
        div(cls := "mdc-radio__background")(
          div(cls := "mdc-radio__outer-circle"),
          div(cls := "mdc-radio__inner-circle"),
        ),
      ),
      label(`for` := radioId)(description)
    )
  }

  object tags {
    val section = typedTag[HTMLElement]("section")
    val main = typedTag[HTMLElement]("main")
    val aside = typedTag[HTMLElement]("aside")
    val nav = typedTag[HTMLElement]("nav")
  }
  import tags._

  // TODO: migrate mdc-toolbar to Top App Bar when fixed TAB support is released (Apr 2018: it is just in master)
  def toolbar(leftButton: Frag, title: String, actionButtons: Seq[Frag] = Seq()) = header(cls:="mdc-toolbar mdc-toolbar--fixed")(
    div(cls:="mdc-toolbar__row")(
      section(cls:="mdc-toolbar__section mdc-toolbar__section--align-start")(
        leftButton,
        span(cls:="mdc-toolbar__title")(title)
      ),
      actionButtons match{
        case Seq() => ""
        case nonEmptyActionButtons => section(cls:="mdc-toolbar__section mdc-toolbar__section--align-end", role:="toolbar")(nonEmptyActionButtons)
      }
    )
  )

  def dialogHeader(title: String, backLink: RoutingState)(implicit applicationContext: ApplicationContext) = toolbar(
    a(href:=linkTo(backLink), cls:="material-icons mdc-toolbar__menu-icon mdc-ripple-surface")("clear"),
    title,
    Seq(button(cls:="material-icons mdc-toolbar__icon")("done"))
  )

  def appHeader(title: String)(implicit applicationContext: ApplicationContext): JsDom.TypedTag[Element] = toolbar(
    span(cls:="material-icons mdc-toolbar__menu-icon mdc-ripple-surface", onclick := {(_: Any) => {applicationContext.drawer.open = true}})("menu"),
    title
  )

  def appContent(frags: Frag*): JsDom.TypedTag[HTMLElement] = main(role := "main", cls := "mdc-toolbar-fixed-adjust")(frags)

  val Descriptions = ListMap[State, String](
    State.Unknown -> "Není zaznamenán/zaznamenána na předchozím stanovišti",
    State.OnWay -> "Na cestě sem (z předchozího stanoviště)",
    State.Arrived -> "Na tomto stanovišti",
    State.Departed -> "Odešel/odešla z tohoto stanoviště",
    State.GivenUpThere -> "Skončil/skončila na tomto stanovišti",
    State.GivenUpBefore -> "Skončil/skončila před tímto stanovištěm",
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

  def participantSelectionListItemStructure(deleteHandlerOption: Option[() => Unit], icon: String, namePlaceholder: Frag, statusPlaceholder: Frag, additionalClasses: String = ""): JsDom.TypedTag[LI] = {
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
