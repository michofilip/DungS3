package src.game.entity

import src.data.file.{FileReader, FileWriter}
import src.data.model.EntityEntry
import src.data.repository.EntityPrototypeRepository
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.io.File
import java.util.UUID

class EntityService(using entityPrototypeRepository: EntityPrototypeRepository):

    def createEntity(id: UUID,
                     name: String,
                     timestamp: Timestamp,
                     state: Option[State] = None,
                     position: Option[Position] = None,
                     direction: Option[Direction] = None): Option[Entity] =

        entityPrototypeRepository.findById(name).map { entityPrototype =>

            val validState = entityPrototype.getValidatedState(state)
            val validPosition = entityPrototype.getValidatedPosition(position)
            val validDirection = entityPrototype.getValidatedDirection(direction)
            val physicsSelector = entityPrototype.physicsSelector
            val animationSelector = entityPrototype.animationSelector

            Entity(
                id = id,
                name = name,
                timestamp = timestamp,
                state = validState,
                position = validPosition,
                direction = validDirection,
                physicsSelector = physicsSelector,
                animationSelector = animationSelector
            )
        }

    def loadEntitiesFromFile(file: File): Seq[Entity] =
        FileReader.readFile(file, EntityEntry.reader)
            .flatMap(convertToEntity)

    def saveEntitiesToFile(file: File, entities: Seq[Entity]): Unit =
        FileWriter.writeFile(file, entities.map(convertToEntry), EntityEntry.writer)

    private def convertToEntity(entityEntry: EntityEntry): Option[Entity] =
        val position = for {
            x <- entityEntry.x
            y <- entityEntry.y
        } yield {
            Position(x, y)
        }

        createEntity(
            id = UUID.fromString(entityEntry.id),
            name = entityEntry.name,
            timestamp = Timestamp(entityEntry.timestamp),
            state = entityEntry.state,
            position = position,
            direction = entityEntry.direction
        )

    private def convertToEntry(entity: Entity): EntityEntry =
        EntityEntry(
            id = entity.id.toString,
            name = entity.name,
            timestamp = entity.timestamp.milliseconds,
            state = entity.state,
            x = entity.position.map(_.x),
            y = entity.position.map(_.y),
            direction = entity.direction
        )
