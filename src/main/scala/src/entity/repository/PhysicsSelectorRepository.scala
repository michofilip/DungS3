package src.entity.repository

import src.entity.selector.PhysicsSelector

trait PhysicsSelectorRepository:
    def findByName(name: String): Option[PhysicsSelector]
