package src.game.service

import src.data.repository.{AnimationRepository, AnimationSelectorRepository, EntityPrototypeRepository, FrameRepository, PhysicsRepository, PhysicsSelectorRepository}
import src.game.service.serialization.{EntitySerializationService, GameStateSerializationService}

class Engine():

    private val frameRepository = new FrameRepository
    private val animationRepository = new AnimationRepository(frameRepository)
    private val animationSelectorRepository = new AnimationSelectorRepository(animationRepository)
    private val physicsRepository = new PhysicsRepository
    private val physicsSelectorRepository = new PhysicsSelectorRepository(physicsRepository)
    private val entityPrototypeRepository = new EntityPrototypeRepository(physicsSelectorRepository, animationSelectorRepository)

    val entityConverter = new EntityConverter(entityPrototypeRepository)
    private val entitySerializationService = new EntitySerializationService(entityConverter)
    private val eventProcessor = new EventProcessor(entityConverter)
    val gameStateProcessor = new GameStateProcessor(eventProcessor)
    private val gameStateSerializationService = new GameStateSerializationService(entitySerializationService)
    val gameStateFileProcessor = new GameStateFileProcessor(gameStateSerializationService)
