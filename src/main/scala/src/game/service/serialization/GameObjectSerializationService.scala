package src.game.service.serialization

import src.data.model.{GameObjectEntity, PhysicsEntity}
import src.data.repository.GameObjectPrototypeRepository
import src.game.gameobject.GameObject
import src.game.gameobject.parts.state.State
import src.game.service.GameObjectConverter
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.util.Try
import scala.xml.{Node, NodeSeq, PrettyPrinter, XML}

class GameObjectSerializationService private(gameObjectConverter: GameObjectConverter):

    def toXml(gameObject: GameObject): Node =
        gameObjectConverter.toEntity(gameObject).toXml

    def fromXml(xml: Node): Try[GameObject] =
        GameObjectEntity.fromXml(xml)
            .flatMap(gameObjectConverter.fromEntity)

object GameObjectSerializationService:

    private lazy val gameObjectSerializationService = new GameObjectSerializationService(GameObjectConverter())

    def apply(): GameObjectSerializationService = gameObjectSerializationService