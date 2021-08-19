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
                physicsSelectorRepository.findById(physicsSelectorId).map { physicsSelector =>
                    Success {
                        physicsSelector
                    }
                }.getOrElse {
                    Failure {
                        new NoSuchElementException(s"PhysicsSelectorId id: $physicsSelectorId not found!")
                    }
                }
            }.invertTry

            val animationSelector = entityPrototypeEntry.animationSelectorId.map { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId).map { animationSelector =>
                    Success {
                        animationSelector
                    }
                }.getOrElse {
                    Failure {
                        new NoSuchElementException(s"AnimationSelector id: $animationSelectorId not found!")
                    }
                }
            }.invertTry

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
        }.invertTry.map(_.toMap).get

object EntityPrototypeRepository:

    private lazy val entityPrototypeRepository = new EntityPrototypeRepository(PhysicsSelectorRepository(), AnimationSelectorRepository())

    def apply(): EntityPrototypeRepository = entityPrototypeRepository