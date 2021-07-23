package src.game.service

import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.service.serialization.{EntitySerializationService, GameStateSerializationService}

class Engine():
    
    val entityConverter: EntityConverter = EntityConverter()
    val gameStateProcessor: GameStateProcessor = GameStateProcessor()
    val gameStateFileProcessor: GameStateFileProcessor = GameStateFileProcessor()
