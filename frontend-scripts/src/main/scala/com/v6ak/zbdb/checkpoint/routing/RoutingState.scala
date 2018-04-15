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
case object TableState extends ContainerRoutingState(Some(RootState))
case object StatisticsState extends ContainerRoutingState(Some(RootState))
case object AboutState extends ContainerRoutingState(Some(RootState))
case object ArrivalState extends ContainerRoutingState(Some(TableState))
case object DepartureState extends ContainerRoutingState(Some(TableState))
case object EndState extends ContainerRoutingState(Some(TableState))
