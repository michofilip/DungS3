package src.data.repository

import src.data.Resources
import src.data.model.{AnimationSelectorEntry, EntityPrototypeEntry}
import src.game.entity.EntityPrototype
import src.game.entity.parts.physics.PhysicsSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class EntityPrototypeRepository private(physicsSelectorRepository: PhysicsSelectorRepository,
                                              animationSelectorRepository: AnimationSelectorRepository) extends Repository[String, EntityPrototype] :

    override protected val dataById: Map[String, EntityPrototype] =
        def entityPrototypeFrom(entityPrototypeEntry: EntityPrototypeEntry): Try[EntityPrototype] =
            val physicsSelector = entityPrototypeEntry.physicsSelectorId.map { physicsSelectorId =>
                physicsSelectorRepository.findById(physicsSelectorId).toTry {
                    new NoSuchElementException(s"PhysicsSelectorId id: $physicsSelectorId not found!")
                }
            }.toTryOption

            val animationSelector = entityPrototypeEntry.animationSelectorId.map { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId).toTry {
                    new NoSuchElementException(s"AnimationSelector id: $animationSelectorId not found!")
                }
            }.toTryOption

            for
                physicsSelector <- physicsSelector
                animationSelector <- animationSelector
            yield
                EntityPrototype(
                    name = entityPrototypeEntry.name,
                    availableStates = entityPrototypeEntry.availableStates,
                    hasPosition = entityPrototypeEntry.hasPosition,
                    hasDirection = entityPrototypeEntry.hasDirection,
                    physicsSelector = physicsSelector,
                    layer = entityPrototypeEntry.layer,
                    animationSelector = animationSelector
                )

        val xml = XML.load(Resources.entityPrototypes.reader())

        (xml \ "EntityPrototype").map { node =>
            for
                entityPrototypeEntry <- EntityPrototypeEntry.fromXML(node)
                entityPrototype <- entityPrototypeFrom(entityPrototypeEntry)
            yield
                entityPrototypeEntry.name -> entityPrototype
        }.toTrySeq.map(_.toMap).get

object EntityPrototypeRepository:

    private lazy val entityPrototypeRepository = new EntityPrototypeRepository(PhysicsSelectorRepository(), AnimationSelectorRepository())

    def apply(): EntityPrototypeRepository = entityPrototypeRepository