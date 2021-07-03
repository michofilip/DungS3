package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.EntityPrototypeEntry
import src.game.entity.EntityPrototype
import src.game.entity.selector.{AnimationSelector, PhysicsSelector}

class EntityPrototypeRepository(using physicsSelectorRepository: PhysicsSelectorRepository,
                                animationSelectorRepository: AnimationSelectorRepository) extends Repository[String, EntityPrototype] :

    override protected val dataById: Map[String, EntityPrototype] =
        def convertToEntityPrototype(entityPrototypeEntry: EntityPrototypeEntry): EntityPrototype =
            val physicsSelector = entityPrototypeEntry.physicsSelectorId.flatMap { physicsSelectorId =>
                physicsSelectorRepository.findById(physicsSelectorId)
            }.getOrElse(PhysicsSelector.empty)

            val animationSelector = entityPrototypeEntry.animationSelectorId.flatMap { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId)
            }.getOrElse(AnimationSelector.empty)

            EntityPrototype(
                name = entityPrototypeEntry.name,
                availableStates = entityPrototypeEntry.availableStates,
                hasPosition = entityPrototypeEntry.hasPosition,
                hasDirection = entityPrototypeEntry.hasDirection,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )

        FileReader.readFile(Resources.entityPrototypeEntriesFile, EntityPrototypeEntry.reader)
            .map(entityPrototype => entityPrototype.name -> convertToEntityPrototype(entityPrototype))
            .toMap
