package com.v6ak.zbdb.checkpoint.routing

import io.udash._

class RoutingRegistryDef extends RoutingRegistry[RoutingState] {
  def matchUrl(url: Url): RoutingState =
    url2State.applyOrElse(
      url.value.stripSuffix("/"),
      (_: String) => TableState
    )

  def matchState(state: RoutingState): Url =
    Url(state2Url.apply(state))

  private val (url2State, state2Url) = bidirectional {
    case "/" => TableState
    case "/statistics" => StatisticsState
    case "/about" => AboutState
    case "/arrival" => ArrivalState
    case "/departure" => DepartureState
    case "/end" => EndState
  }

}