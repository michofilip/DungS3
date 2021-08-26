package src.game.service

import src.data.model.{GameObjectEntry, PhysicsEntry}
import src.data.repository.GameObjectPrototypeRepository
import src.game.gameobject.GameObject
import src.game.gameobject.parts.state.StateProperty
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.util.{Failure, Success, Try}
import scala.xml.{Node, PrettyPrinter, XML}

class GameObjectConverter private(gameObjectPrototypeRepository: GameObjectPrototypeRepository):

    def fromEntry(gameObjectEntry: GameObjectEntry): Try[GameObject] =
        gameObjectPrototypeRepository.findById(gameObjectEntry.name).map { gameObjectPrototype =>

            val stateProperty = gameObjectPrototype.getStateProperty(gameObjectEntry.state, gameObjectEntry.stateTimestamp.map(Timestamp.apply))
            val positionProperty = gameObjectPrototype.getPositionProperty(gameObjectEntry.position, gameObjectEntry.direction, gameObjectEntry.positionTimestamp.map(Timestamp.apply))
            val physicsProperty = gameObjectPrototype.getPhysicsProperty
            val graphicsProperty = gameObjectPrototype.getGraphicsProperty

            Success {
                new GameObject(
                    id = UUID.fromString(gameObjectEntry.id),
                    name = gameObjectEntry.name,
                    creationTimestamp = Timestamp(gameObjectEntry.creationTimestamp),
                    stateProperty = stateProperty,
                    positionProperty = positionProperty,
                    physicsProperty = physicsProperty,
                    graphicsProperty = graphicsProperty
                )
            }
        }.getOrElse {
            Failure {
                new NoSuchElementException(s"GameObjectPrototype name: ${gameObjectEntry.name} not found!")
            }
        }

    def toEntry(gameObject: GameObject): GameObjectEntry =
        GameObjectEntry(
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