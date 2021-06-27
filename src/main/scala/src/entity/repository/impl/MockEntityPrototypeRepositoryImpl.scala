package src.entity.repository.impl

import src.entity.EntityPrototype
import src.entity.parts.{Direction, Graphics, Physics}
import src.entity.repository.{EntityPrototypeRepository, GraphicsSelectorRepository, PhysicsSelectorRepository}
import src.entity.selector.{GraphicsSelector, PhysicsSelector}

final class MockEntityPrototypeRepositoryImpl(override protected val physicsSelectorRepository: PhysicsSelectorRepository,
                                              override protected val graphicsSelectorRepository: GraphicsSelectorRepository) extends EntityPrototypeRepository :

    override def findByName(name: String): Option[EntityPrototype] =
        for {
            physicsSelector <- physicsSelectorRepository.findByName(name)
            graphicsSelector <- graphicsSelectorRepository.findByName(name)
        } yield {
            EntityPrototype(
                name = name,
                availableStates = Seq.empty,
                hasPosition = true,
                hasDirection = true,
                physicsSelector = physicsSelector,
                graphicsSelector = graphicsSelector
            )
        }
