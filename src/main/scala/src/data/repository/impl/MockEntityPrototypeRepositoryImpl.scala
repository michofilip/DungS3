package src.data.repository.impl

import src.data.repository.{AnimationSelectorRepository, EntityPrototypeRepository, PhysicsSelectorRepository}
import src.game.entity.EntityPrototype
import src.game.entity.parts.{Direction, Physics}
import src.game.entity.selector.{AnimationSelector, PhysicsSelector}

final class MockEntityPrototypeRepositoryImpl(override protected val physicsSelectorRepository: PhysicsSelectorRepository,
                                              override protected val animationSelectorRepository: AnimationSelectorRepository) extends EntityPrototypeRepository :

    override def findByName(name: String): Option[EntityPrototype] =
        val physicsSelector = physicsSelectorRepository.findByName(name).getOrElse(PhysicsSelector.empty)
        val animationSelector = animationSelectorRepository.findByName(name).getOrElse(AnimationSelector.empty)

        Some {
            EntityPrototype(
                name = name,
                availableStates = Seq.empty,
                hasPosition = true,
                hasDirection = true,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )
        }
