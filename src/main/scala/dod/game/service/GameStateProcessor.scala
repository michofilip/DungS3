package dod.game.service

import dod.data.repository.{AnimationRepository, AnimationSelectorRepository, GameObjectPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import dod.game.GameState
import dod.game.service.serialization.{GameObjectSerializationService, GameStateSerializationService}

class GameStateProcessor private(eventProcessor: EventProcessor):

    def processNextEvent(gameState: GameState): GameState =
        gameState.events match 
            case event +: otherEvents =>
                eventProcessor.processEvent(event, gameState.updated(events = otherEvents))

            case _ => gameState
        

object GameStateProcessor:

    private lazy val gameStateProcessor = new GameStateProcessor(EventProcessor())

    def apply(): GameStateProcessor = gameStateProcessor