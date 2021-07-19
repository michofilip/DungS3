//package src.game.event
//
//import src.game.GameState
//import src.game.event.Event.EventResponse
//
//object TimeEvent:
//
//    case object StartTimer extends Event :
//        override def applyTo(gameState: GameState): EventResponse =
//            (gameState.updated(timer = gameState.timer.started), Vector.empty)
//
//    case object StopTimer extends Event :
//        override def applyTo(gameState: GameState): EventResponse =
//            (gameState.updated(timer = gameState.timer.stopped), Vector.empty)
