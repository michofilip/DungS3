package src.game.service

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.entity.Entity
import src.game.entity.parts.state.StateProperty
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.util.{Failure, Success, Try}
import scala.xml.{Node, PrettyPrinter, XML}

class EntityConverter private(entityPrototypeRepository: EntityPrototypeRepository):

    def convertToEntity(entityEntry: EntityEntry): Try[Entity] =
        entityPrototypeRepository.findById(entityEntry.name).map { entityPrototype =>

            val stateProperty = entityPrototype.getStateProperty(entityEntry.state, entityEntry.stateTimestamp.map(Timestamp.apply))
            val positionProperty = entityPrototype.getPositionProperty(entityEntry.position, entityEntry.direction, entityEntry.positionTimestamp.map(Timestamp.apply))
            val physicsProperty = entityPrototype.getPhysicsProperty
            val graphicsProperty = entityPrototype.getGraphicsProperty

            Success {
                new Entity(
                    id = UUID.fromString(entityEntry.id),
                    name = entityEntry.name,
                    creationTimestamp = Timestamp(entityEntry.creationTimestamp),
                    stateProperty = stateProperty,
                    positionProperty = positionProperty,
                    physicsProperty = physicsProperty,
                    graphicsProperty = graphicsProperty
                )
            }
        }.getOrElse {
            Failure {
                new NoSuchElementException(s"EntityPrototype name: ${entityEntry.name} not found!")
            }
        }

    def convertToEntityEntry(entity: Entity): EntityEntry =
        EntityEntry(
            id = entity.id.toString,
            name = entity.name,
            creationTimestamp = entity.creationTimestamp.milliseconds,
            state = entity.state,
            stateTimestamp = entity.stateTimestamp.map(_.milliseconds),
            x = entity.position.map(_.x),
            y = entity.position.map(_.y),
            direction = entity.direction,
            positionTimestamp = entity.positionTimestamp.map(_.milliseconds)
        )

object EntityConverter:

    private lazy val entityConverter = new EntityConverter(EntityPrototypeRepository())

    def apply(): EntityConverter = entityConverter