package src.game.service

import src.game.GameState

class GameStateService(eventProcessorService: EventProcessorService):
    def processNextEvent(gameState: GameState): GameState =
        gameState.events match {
            case event +: otherEvents =>
                eventProcessorService.processEvent(event, gameState.updated(events = otherEvents))

            case _ => gameState
        }
