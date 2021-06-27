package src.entity.repository

import src.entity.EntityPrototype

trait EntityPrototypeRepository:
    protected val physicsSelectorRepository: PhysicsSelectorRepository
    protected val graphicsSelectorRepository: GraphicsSelectorRepository

    def findByName(name: String): Option[EntityPrototype]
