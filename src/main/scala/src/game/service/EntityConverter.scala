package src.game.service

import src.data.model.{EntityEntry, PhysicsEntry}
import src.data.repository.EntityPrototypeRepository
import src.game.entity.Entity
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import java.util.UUID
import scala.xml.{Node, PrettyPrinter, XML}

class EntityConverter(entityPrototypeRepository: EntityPrototypeRepository):

    def convertToEntity(entityEntry: EntityEntry): Option[Entity] =
        entityPrototypeRepository.findById(entityEntry.name).map { entityPrototype =>

            val validState = entityPrototype.getValidatedState(entityEntry.state)
            val validPosition = entityPrototype.getValidatedPosition(entityEntry.position)
            val validDirection = entityPrototype.getValidatedDirection(entityEntry.direction)
            val physicsSelector = entityPrototype.physicsSelector
            val animationSelector = entityPrototype.animationSelector

            Entity(
                id = UUID.fromString(entityEntry.id),
                name = entityEntry.name,
                timestamp = Timestamp(entityEntry.timestamp),
                state = validState,
                position = validPosition,
                direction = validDirection,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )
        }

    def convertToEntityEntry(entity: Entity): EntityEntry =
        EntityEntry(
            id = entity.id.toString,
            name = entity.name,
            timestamp = entity.timestamp.milliseconds,
            state = entity.state,
            x = entity.position.map(_.x),
            y = entity.position.map(_.y),
            direction = entity.direction
        )
        