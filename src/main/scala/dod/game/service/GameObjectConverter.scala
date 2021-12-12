package dod.game.service

import dod.data.model.{GameObjectEntity, PhysicsEntity}
import dod.data.repository.GameObjectPrototypeRepository
import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.commons.CommonsProperty
import dod.game.gameobject.parts.state.StateProperty
import dod.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.util.{Failure, Success, Try}
import scala.xml.{Node, PrettyPrinter, XML}

class GameObjectConverter private(gameObjectPrototypeRepository: GameObjectPrototypeRepository):

    def fromEntity(gameObjectEntity: GameObjectEntity): Try[GameObject] =
        gameObjectPrototypeRepository.findById(gameObjectEntity.name).map { gameObjectPrototype =>

            val commonsProperty = CommonsProperty(gameObjectEntity.name, Timestamp(gameObjectEntity.creationTimestamp))
            val stateProperty = gameObjectPrototype.getStateProperty(gameObjectEntity.state, gameObjectEntity.stateTimestamp.map(Timestamp.apply))
            val positionProperty = gameObjectPrototype.getPositionProperty(gameObjectEntity.position, gameObjectEntity.direction, gameObjectEntity.positionTimestamp.map(Timestamp.apply))
            val physicsProperty = gameObjectPrototype.getPhysicsProperty
            val graphicsProperty = gameObjectPrototype.getGraphicsProperty

            Success {
                new GameObject(
                    id = UUID.fromString(gameObjectEntity.id),
                    commonsProperty = commonsProperty,
                    stateProperty = stateProperty,
                    positionProperty = positionProperty,
                    physicsProperty = physicsProperty,
                    graphicsProperty = graphicsProperty
                )
            }
        }.getOrElse {
            Failure {
                new NoSuchElementException(s"GameObjectPrototype name: ${gameObjectEntity.name} not found!")
            }
        }

    def toEntity(gameObject: GameObject): GameObjectEntity =
        GameObjectEntity(
            id = gameObject.id.toString,
            name = gameObject.name,
            creationTimestamp = gameObject.creationTimestamp.milliseconds,
            state = gameObject.state,
            stateTimestamp = gameObject.stateTimestamp.map(_.milliseconds),
            x = gameObject.position.map(_.x),
            y = gameObject.position.map(_.y),
            direction = gameObject.direction,
            positionTimestamp = gameObject.positionTimestamp.map(_.milliseconds)
        )

object GameObjectConverter:

    private lazy val gameObjectConverter = new GameObjectConverter(GameObjectPrototypeRepository())

    def apply(): GameObjectConverter = gameObjectConverter