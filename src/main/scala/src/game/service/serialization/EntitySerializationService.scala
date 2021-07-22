package src.game.service.serialization

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.entity.Entity
import src.game.entity.parts.{Direction, Position, State}
import src.game.service.EntityConverter
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.xml.{Node, NodeSeq, PrettyPrinter, XML}

class EntitySerializationService(entityConverter: EntityConverter):

    def toXml(entity: Entity): Node =
        entityConverter.convertToEntityEntry(entity).toXml

    def fromXml(xml: Node): Option[Entity] =
        EntityEntry.fromXML(xml)
            .flatMap(entityConverter.convertToEntity)
