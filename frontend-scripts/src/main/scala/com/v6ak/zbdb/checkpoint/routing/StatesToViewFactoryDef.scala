package com.v6ak.zbdb.checkpoint.routing

import com.v6ak.zbdb.checkpoint.ApplicationContext
import com.v6ak.zbdb.checkpoint.views._
import io.udash._

class StatesToViewFactoryDef(implicit applicationContext: ApplicationContext) extends ViewFactoryRegistry[RoutingState] {
  def matchStateToResolver(state: RoutingState): ViewFactory[_ <: RoutingState] =
    state match {
      case RootState => new StaticViewFactory(() => new RootView())
      case SheetOpenedState(sheetId) => new StaticViewFactory(() => new OpenedSheetView(sheetId))
      case SelectSheetState => new StaticViewFactory(() => new SelectSheetView())
      case TableState(sheetId) => new StaticViewFactory(() => new TableView(sheetId))
      case StatisticsState(sheetId) => new StaticViewFactory(() => new StatisticsView(sheetId))
      case DepartureState(sheetId) => new StaticViewFactory(() => new DepartureView(sheetId))
      case ArrivalState(sheetId) => new StaticViewFactory(() => new ArrivalView(sheetId))
      case EndState(sheetId) => new StaticViewFactory(() => new EndView(sheetId))
      case AboutState(sheetId) => new StaticViewFactory(() => new AboutView())
    }
}