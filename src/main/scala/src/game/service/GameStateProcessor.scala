package src.game.service

import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.GameState
import src.game.service.serialization.{EntitySerializationService, GameStateSerializationService}

class GameStateProcessor private(eventProcessor: EventProcessor):

    def processNextEvent(gameState: GameState): GameState =
        gameState.events match {
            case event +: otherEvents =>
                eventProcessor.processEvent(event, gameState.updated(events = otherEvents))

            case _ => gameState
        }

object GameStateProcessor:

    private lazy val gameStateProcessor = new GameStateProcessor(EventProcessor())

    def apply(): GameStateProcessor = gameStateProcessor