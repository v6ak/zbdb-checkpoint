package com.v6ak.zbdb.checkpoint.routing

import io.udash._

class RoutingRegistryDef extends RoutingRegistry[RoutingState] {
  def matchUrl(url: Url): RoutingState =
    url2State.applyOrElse(
      url.value.stripSuffix("/"),
      (_: String) => SelectSheetState
    )

  def matchState(state: RoutingState): Url =
    Url(state2Url.apply(state))

  private val (url2State, state2Url) = bidirectional {
    case "/" => SelectSheetState
    case "/sheet" / sheetId / "table" => TableState(sheetId)
    case "/sheet" / sheetId / "statistics" => StatisticsState(sheetId)
    case "/sheet" / sheetId / "about" => AboutState(sheetId)
    case "/sheet" / sheetId / "arrival" => ArrivalState(sheetId)
    case "/sheet" / sheetId / "departure" => DepartureState(sheetId)
    case "/sheet" / sheetId / "end" => EndState(sheetId)
  }

}