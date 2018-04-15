package com.v6ak.zbdb.checkpoint.routing

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.views._
import io.udash._

class StatesToViewFactoryDef(implicit applicationContext: ApplicationContext) extends ViewFactoryRegistry[RoutingState] {
  def matchStateToResolver(state: RoutingState): ViewFactory[_ <: RoutingState] =
    state match {
      case RootState => new StaticViewFactory(() => new RootView())
      case TableState => new StaticViewFactory(() => new TableView())
      case StatisticsState => new StaticViewFactory(() => new StatisticsView())
      case DepartureState => new StaticViewFactory(() => new DepartureView())
      case ArrivalState => new StaticViewFactory(() => new ArrivalView())
      case EndState => new StaticViewFactory(() => new EndView())
      case AboutState => new StaticViewFactory(() => new AboutView())
    }
}