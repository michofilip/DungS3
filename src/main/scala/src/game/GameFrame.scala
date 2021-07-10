package src.game

import src.game.event.Event

class GameFrame(val gameState: GameState, private val events: Vector[Event]):

    //    def nextFrame(externalEvents: Vector[Event] = Vector.empty): GameFrame =
    //        val (nextGameState, nextEvents) = (events ++ externalEvents).foldLeft((gameState, Vector.empty[Event])) { case ((gameState, nextEvents), event) =>
    //            val (newGameState, newEvents) = event.applyTo(gameState)
    //
    //            (newGameState, nextEvents ++ newEvents)
    //        }
    //
    //        GameFrame(nextGameState, nextEvents)

    def nextFrame: GameFrame =
        events match {
            case event +: otherEvents =>
                event.applyTo(gameState) match {
                    case (gameState, responseEvents) =>
                        GameFrame(gameState, otherEvents ++ responseEvents)
                }

            case _ => this
        }

    override def toString: String =
        val eventsStr = events.mkString("Events(", ", ", ")")
        s"GameFrame(gameState=$gameState, events=$eventsStr)"
