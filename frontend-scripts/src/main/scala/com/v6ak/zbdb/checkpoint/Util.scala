package com.v6ak.zbdb.checkpoint

import com.v6ak.zbdb.checkpoint.routing.RoutingState

object Util {
  implicit def linkTo(state: RoutingState)(implicit applicationContext: ApplicationContext): String = "#"+applicationContext.application.matchState(state).value
}
