package dod.game.service.serialization

import dod.data.model.{GameObjectEntity, PhysicsEntity}
import dod.data.repository.GameObjectPrototypeRepository
import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.state.State
import dod.game.service.GameObjectConverter
import dod.game.temporal.Timestamp

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