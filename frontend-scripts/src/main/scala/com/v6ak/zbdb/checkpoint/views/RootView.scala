package com.v6ak.zbdb.checkpoint.views

import com.v6ak.mdc.drawer.MDCTemporaryDrawer
import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.Templates.tags._
import com.v6ak.zbdb.checkpoint.Util.linkTo
import com.v6ak.zbdb.checkpoint.routing.{AboutState, RoutingState, StatisticsState, TableState}
import io.udash.{ContainerView, _}
import org.scalajs.dom.Element
import scalatags.JsDom.all._
import scalatags.{JsDom, generic}

class RootView(applicationContext: ApplicationContext) extends ContainerView {

  private def item(state: RoutingState, icon: String, name: String): JsDom.Modifier = {
    val classNameProp = applicationContext.application.currentStateProperty.transform(currentState => if(currentState == state) " mdc-list-item--activated" else "").transform("mdc-list-item"+_)
    a(cls.bind(classNameProp), href := linkTo(state))(
      i(cls := "material-icons mdc-list-item__graphic", attr("aria-hidden") := "true", icon),
      name
    )
  }

  private val drawerDom = aside(cls := "mdc-drawer mdc-drawer--temporary mdc-typography")(
    nav(cls := "mdc-drawer__drawer")(
      header(cls := "mdc-drawer__header")(
        div(cls := "mdc-drawer__header-content")(
          div("Stanoviště č.")(applicationContext.pos, ": ", applicationContext.name)
        )
      ),
      section(cls := "mdc-drawer__content", id := "main-menu")(
        nav(cls := "mdc-list")(
          item(TableState, "list", "Tabulka"),
          item(StatisticsState, "show_chart", "Statistiky"),
        ),
        hr(cls := "mdc-list-divider"),
        nav(cls := "mdc-list")(
          item(AboutState, "info", "O aplikaci"),
        )
      )
    )
  ).render

  private val drawer = new MDCTemporaryDrawer(drawerDom)

  {
    applicationContext.drawer = drawer // TODO: find a better way
    applicationContext.application.onStateChange((_) => drawer.open = false)
  }

  override def getTemplate: generic.Modifier[Element] = Seq[Frag](
    drawerDom,
    childViewContainer
  )
}

