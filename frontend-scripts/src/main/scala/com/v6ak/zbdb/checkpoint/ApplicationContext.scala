package com.v6ak.zbdb.checkpoint


import com.v6ak.mdc.drawer.MDCTemporaryDrawer
import com.v6ak.zbdb.checkpoint.routing.{RoutingRegistryDef, RoutingState, StatesToViewFactoryDef}
import io.udash._

case class ApplicationContext() {

  private val routingRegistry = new RoutingRegistryDef
  private val viewFactoryRegistry = new StatesToViewFactoryDef()(this)
  var drawer: MDCTemporaryDrawer = _

  val application = new Application[RoutingState](routingRegistry, viewFactoryRegistry)

}
