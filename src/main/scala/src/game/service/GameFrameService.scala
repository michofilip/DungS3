package src.game.service

import src.game.GameFrame
import src.game.event2.EventProcessor

class GameFrameService(eventProcessor: EventProcessor):
    def processNextFrame(gameFrame: GameFrame): GameFrame =
        gameFrame.events match {
            case event +: otherEvents =>
                eventProcessor.processEvent(event, gameFrame.gameState) match {
                    case (gameState, responseEvents) =>
                        GameFrame(gameState, otherEvents ++ responseEvents)
                }

            case _ => gameFrame
        }
