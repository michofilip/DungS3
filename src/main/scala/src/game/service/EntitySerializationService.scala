package src.game.service

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.entity.Entity
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.xml.{Node, NodeSeq, PrettyPrinter, XML}

class EntitySerializationService(entityService: EntityService):

    def toXml(entity: Entity): Node =
        def entityEntryToXml(entityEntry: EntityEntry): Node = entityEntry match {
            case EntityEntry(id, name, timestamp, state, x, y, direction) =>
                <Entity>
                    <id> {id} </id>
                    <name> {name} </name>
                    <timestamp> {timestamp} </timestamp>
                    {state.fold(NodeSeq.Empty) { state => <state> {state} </state> }}
                    {x.fold(NodeSeq.Empty) { x => <x> {x} </x> }}
                    {y.fold(NodeSeq.Empty) { y => <y> {y} </y> }}
                    {direction.fold(NodeSeq.Empty) { direction => <direction> {direction} </direction> }}
                </Entity>
        }

        entityEntryToXml {
            entityService.convertToEntityEntry {
                entity
            }
        }

    def fromXml(xml: Node): Option[Entity] =
        EntityEntry.fromXML(xml)
            .flatMap(entityService.convertToEntity)
