package src.game.service.serialization

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.gameobject.Entity
import src.game.gameobject.parts.state.State
import src.game.service.EntityConverter
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.util.Try
import scala.xml.{Node, NodeSeq, PrettyPrinter, XML}

class EntitySerializationService private(entityConverter: EntityConverter):

    def toXml(entity: Entity): Node =
        entityConverter.convertToEntityEntry(entity).toXml

    def fromXml(xml: Node): Try[Entity] =
        EntityEntry.fromXml(xml)
            .flatMap(entityConverter.convertToEntity)

object EntitySerializationService:

    private lazy val entitySerializationService = new EntitySerializationService(EntityConverter())

    def apply(): EntitySerializationService = entitySerializationService