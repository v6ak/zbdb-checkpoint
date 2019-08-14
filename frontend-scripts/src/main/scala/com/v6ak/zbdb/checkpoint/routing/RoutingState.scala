package com.v6ak.zbdb.checkpoint.routing

import io.udash._

sealed abstract class RoutingState(val parentState: Option[ContainerRoutingState]) extends State {
  override type HierarchyRoot = RoutingState
}

sealed abstract class ContainerRoutingState(parentState: Option[ContainerRoutingState])
  extends RoutingState(parentState) with ContainerState

//sealed abstract class FinalRoutingState(parentState: Option[ContainerRoutingState])
//  extends RoutingState(parentState) with FinalState

case object RootState extends ContainerRoutingState(None)
case object SelectSheetState extends ContainerRoutingState(Some(RootState))
case class SheetOpenedState(sheetId: String) extends ContainerRoutingState(Some(RootState))
case class TableState(sheetId: String) extends ContainerRoutingState(Some(SheetOpenedState(sheetId)))
case class StatisticsState(sheetId: String) extends ContainerRoutingState(Some(SheetOpenedState(sheetId)))
case class AboutState(sheetId: String) extends ContainerRoutingState(Some(SheetOpenedState(sheetId)))
case class ArrivalState(sheetId: String) extends ContainerRoutingState(Some(TableState(sheetId)))
case class DepartureState(sheetId: String) extends ContainerRoutingState(Some(TableState(sheetId)))
case class EndState(sheetId: String) extends ContainerRoutingState(Some(TableState(sheetId)))
