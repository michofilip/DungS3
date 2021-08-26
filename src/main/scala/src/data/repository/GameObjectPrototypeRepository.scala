package src.data.repository

import src.data.Resources
import src.data.model.{AnimationSelectorEntry, GameObjectPrototypeEntry}
import src.game.gameobject.GameObjectPrototype
import src.game.gameobject.parts.physics.PhysicsSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class GameObjectPrototypeRepository private(physicsSelectorRepository: PhysicsSelectorRepository,
                                                  animationSelectorRepository: AnimationSelectorRepository) extends Repository[String, GameObjectPrototype] :

    override protected val dataById: Map[String, GameObjectPrototype] =
        def gameObjectPrototypeFrom(gameObjectPrototypeEntry: GameObjectPrototypeEntry): Try[GameObjectPrototype] =
            val physicsSelector = gameObjectPrototypeEntry.physicsSelectorId.map { physicsSelectorId =>
                physicsSelectorRepository.findById(physicsSelectorId).toTry {
                    new NoSuchElementException(s"PhysicsSelectorId id: $physicsSelectorId not found!")
                }
            }.toTryOption

            val animationSelector = gameObjectPrototypeEntry.animationSelectorId.map { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId).toTry {
                    new NoSuchElementException(s"AnimationSelector id: $animationSelectorId not found!")
                }
            }.toTryOption

            for
                physicsSelector <- physicsSelector
                animationSelector <- animationSelector
            yield
                GameObjectPrototype(
                    name = gameObjectPrototypeEntry.name,
                    availableStates = gameObjectPrototypeEntry.availableStates,
                    hasPosition = gameObjectPrototypeEntry.hasPosition,
                    hasDirection = gameObjectPrototypeEntry.hasDirection,
                    physicsSelector = physicsSelector,
                    layer = gameObjectPrototypeEntry.layer,
                    animationSelector = animationSelector
                )

        val xml = XML.load(Resources.gameObjectPrototypes.reader())

        (xml \ "GameObjectPrototype").map { node =>
            for
                gameObjectPrototypeEntry <- GameObjectPrototypeEntry.fromXML(node)
                gameObjectPrototype <- gameObjectPrototypeFrom(gameObjectPrototypeEntry)
            yield
                gameObjectPrototypeEntry.name -> gameObjectPrototype
        }.toTrySeq.map(_.toMap).get

object GameObjectPrototypeRepository:

    private lazy val gameObjectPrototypeRepository = new GameObjectPrototypeRepository(PhysicsSelectorRepository(), AnimationSelectorRepository())

    def apply(): GameObjectPrototypeRepository = gameObjectPrototypeRepository