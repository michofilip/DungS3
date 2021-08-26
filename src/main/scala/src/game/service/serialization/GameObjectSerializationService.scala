package src.game.service.serialization

import src.data.model.{GameObjectEntry, PhysicsEntry}
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
        gameObjectConverter.toEntry(gameObject).toXml

    def fromXml(xml: Node): Try[GameObject] =
        GameObjectEntry.fromXml(xml)
            .flatMap(gameObjectConverter.fromEntry)

object GameObjectSerializationService:

    private lazy val gameObjectSerializationService = new GameObjectSerializationService(GameObjectConverter())

    def apply(): GameObjectSerializationService = gameObjectSerializationService