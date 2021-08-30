package dod.data.repository

import dod.data.Resources
import dod.data.model.{AnimationSelectorEntity, GameObjectPrototypeEntity}
import dod.game.gameobject.GameObjectPrototype
import dod.game.gameobject.parts.physics.PhysicsSelector
import dod.utils.FileUtils
import dod.utils.TryUtils.*

import java.io.FileInputStream
import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class GameObjectPrototypeRepository private(physicsSelectorRepository: PhysicsSelectorRepository,
                                                  animationSelectorRepository: AnimationSelectorRepository) extends Repository[String, GameObjectPrototype] :

    override protected val dataById: Map[String, GameObjectPrototype] =
        def gameObjectPrototypeFrom(gameObjectPrototypeEntity: GameObjectPrototypeEntity): Try[GameObjectPrototype] =
            val physicsSelector = gameObjectPrototypeEntity.physicsSelectorId.map { physicsSelectorId =>
                physicsSelectorRepository.findById(physicsSelectorId).toTry {
                    new NoSuchElementException(s"PhysicsSelectorId id: $physicsSelectorId not found!")
                }
            }.toTryOption

            val animationSelector = gameObjectPrototypeEntity.animationSelectorId.map { animationSelectorId =>
                animationSelectorRepository.findById(animationSelectorId).toTry {
                    new NoSuchElementException(s"AnimationSelector id: $animationSelectorId not found!")
                }
            }.toTryOption

            for
                physicsSelector <- physicsSelector
                animationSelector <- animationSelector
            yield
                GameObjectPrototype(
                    name = gameObjectPrototypeEntity.name,
                    availableStates = gameObjectPrototypeEntity.availableStates,
                    hasPosition = gameObjectPrototypeEntity.hasPosition,
                    hasDirection = gameObjectPrototypeEntity.hasDirection,
                    physicsSelector = physicsSelector,
                    layer = gameObjectPrototypeEntity.layer,
                    animationSelector = animationSelector
                )

        FileUtils.filesInDir(Resources.gameObjectPrototypes).map { file =>
            XML.load(new FileInputStream(file))
        }.flatMap { xml =>
            (xml \ "GameObjectPrototype").map { node =>
                for
                    gameObjectPrototypeEntity <- GameObjectPrototypeEntity.fromXML(node)
                    gameObjectPrototype <- gameObjectPrototypeFrom(gameObjectPrototypeEntity)
                yield
                    gameObjectPrototypeEntity.name -> gameObjectPrototype
            }
        }.toTrySeq.map(_.toMap).get

object GameObjectPrototypeRepository:

    private lazy val gameObjectPrototypeRepository = new GameObjectPrototypeRepository(PhysicsSelectorRepository(), AnimationSelectorRepository())

    def apply(): GameObjectPrototypeRepository = gameObjectPrototypeRepository