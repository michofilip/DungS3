package src.data.repository

import src.data.repository.PhysicsSelectorRepository
import src.game.entity.EntityPrototype

trait EntityPrototypeRepository:
    protected val physicsSelectorRepository: PhysicsSelectorRepository
    protected val animationSelectorRepository: AnimationSelectorRepository

    def findByName(name: String): Option[EntityPrototype]
