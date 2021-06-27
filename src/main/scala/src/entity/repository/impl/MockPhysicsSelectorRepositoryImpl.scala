package src.entity.repository.impl

import src.entity.EntityPrototype
import src.entity.parts.{Direction, Graphics, Physics}
import src.entity.repository.{EntityPrototypeRepository, PhysicsSelectorRepository}
import src.entity.selector.{GraphicsSelector, PhysicsSelector}

final class MockPhysicsSelectorRepositoryImpl() extends PhysicsSelectorRepository :
    override def findByName(name: String): Option[PhysicsSelector] =
        Some {
            PhysicsSelector(
                None -> Physics(solid = true, opaque = true)
            )
        }
