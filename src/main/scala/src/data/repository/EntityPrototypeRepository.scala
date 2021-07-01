package src.data.repository

import src.data.repository.{GraphicsSelectorRepository, PhysicsSelectorRepository}
import src.game.entity.EntityPrototype

trait EntityPrototypeRepository:
    protected val physicsSelectorRepository: PhysicsSelectorRepository
    protected val graphicsSelectorRepository: GraphicsSelectorRepository
    protected val animationSelectorRepository: AnimationSelectorRepository

    def findByName(name: String): Option[EntityPrototype]
