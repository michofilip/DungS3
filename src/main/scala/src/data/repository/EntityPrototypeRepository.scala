package src.data.repository

import src.data.Resources
import src.data.model.{AnimationSelectorEntry, EntityPrototypeEntry}
import src.game.entity.EntityPrototype
import src.game.entity.parts.physics.PhysicsSelector

import scala.xml.XML

final class EntityPrototypeRepository private(physicsSelectorRepository: PhysicsSelectorRepository,
                                              animationSelectorRepository: AnimationSelectorRepository) extends Repository[String, EntityPrototype] :

    override protected val dataById: Map[String, EntityPrototype] =
        def convertToEntityPrototype(entityPrototypeEntry: EntityPrototypeEntry): EntityPrototype =
            val physicsSelector = entityPrototypeEntry.physicsSelectorId.flatMap { physicsSelectorId =>
                physicsSelectorRepository.findById(physicsSelectorId)
            }

            val animationSelector = entityPrototypeEntry.animationSelectorId.flatMap { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId)
            }

            EntityPrototype(
                name = entityPrototypeEntry.name,
                availableStates = entityPrototypeEntry.availableStates,
                hasPosition = entityPrototypeEntry.hasPosition,
                hasDirection = entityPrototypeEntry.hasDirection,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )

        val xml = XML.load(Resources.entityPrototypes.reader())

        (xml \ "EntityPrototype")
            .flatMap(EntityPrototypeEntry.fromXML)
            .map(entityPrototype => entityPrototype.name -> convertToEntityPrototype(entityPrototype))
            .toMap

object EntityPrototypeRepository:

    private lazy val entityPrototypeRepository = new EntityPrototypeRepository(PhysicsSelectorRepository(), AnimationSelectorRepository())

    def apply(): EntityPrototypeRepository = entityPrototypeRepository