package src.data.repository.impl

import src.data.repository.{EntityPrototypeRepository, PhysicsSelectorRepository}
import src.game.entity.EntityPrototype
import src.game.entity.parts.{Direction, Graphics, Physics}
import src.game.entity.selector.{GraphicsSelector, PhysicsSelector}

final class MockPhysicsSelectorRepositoryImpl() extends PhysicsSelectorRepository :
    override def findByName(name: String): Option[PhysicsSelector] =
        Some {
            PhysicsSelector(
                None -> Physics(solid = true, opaque = true)
            )
        }
