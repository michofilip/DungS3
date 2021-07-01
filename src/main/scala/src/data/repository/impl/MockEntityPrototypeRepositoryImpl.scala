package src.data.repository.impl

import src.data.repository.{AnimationSelectorRepository, EntityPrototypeRepository, GraphicsSelectorRepository, PhysicsSelectorRepository}
import src.game.entity.EntityPrototype
import src.game.entity.parts.{Direction, Graphics, Physics}
import src.game.entity.selector.{GraphicsSelector, PhysicsSelector}

final class MockEntityPrototypeRepositoryImpl(override protected val physicsSelectorRepository: PhysicsSelectorRepository,
                                              override protected val graphicsSelectorRepository: GraphicsSelectorRepository,
                                              override protected val animationSelectorRepository: AnimationSelectorRepository) extends EntityPrototypeRepository :

    override def findByName(name: String): Option[EntityPrototype] =
        for {
            physicsSelector <- physicsSelectorRepository.findByName(name)
            graphicsSelector <- graphicsSelectorRepository.findByName(name)
            animationSelector <- animationSelectorRepository.findByName(name)
        } yield {
            EntityPrototype(
                name = name,
                availableStates = Seq.empty,
                hasPosition = true,
                hasDirection = true,
                physicsSelector = physicsSelector,
                graphicsSelector = graphicsSelector,
                animationSelector = animationSelector
            )
        }
