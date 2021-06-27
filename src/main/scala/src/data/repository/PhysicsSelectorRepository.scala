package src.data.repository

import src.game.entity.selector.PhysicsSelector

trait PhysicsSelectorRepository:
    def findByName(name: String): Option[PhysicsSelector]
