package com.v6ak.zbdb.checkpoint


import com.v6ak.mdc.drawer.MDCTemporaryDrawer
import com.v6ak.zbdb.checkpoint.routing.{RoutingRegistryDef, RoutingState, StatesToViewFactoryDef}
import io.udash._

case class ApplicationContext(pos: Int, name: String, year: Int) {

  private val routingRegistry = new RoutingRegistryDef
  private val viewFactoryRegistry = new StatesToViewFactoryDef()(this)
  var drawer: MDCTemporaryDrawer = _

  val application = new Application[RoutingState](routingRegistry, viewFactoryRegistry)

  def title = s"#$pos $name" // TODO: move elsewhere

//  application.onRoutingFailure {
//    application.goTo(ErrorPageState)
//  }

}
