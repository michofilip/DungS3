package src.game.entity

import src.data.repository.EntityPrototypeRepository
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.util.UUID

class EntityFactory(using entityPrototypeRepository: EntityPrototypeRepository):

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

//    def loadEntitiesFromFile(file: File): Seq[Entity] = {
//        FileReader.readFile(file, EntityEntry.reader)
//            .flatMap(convertToEntity)
//    }
//
//    def saveEntitiesToFile(file: File, entities: Seq[Entity]): Unit = {
//        FileWriter.writeFile(file, entities.map(convertToEntry), EntityEntry.writer)
//    }
//
//    private def convertToEntity(entityEntry: EntityEntry): Option[Entity] = entityEntry match {
//        case EntityEntry(id, entityName, timestamp, state, x, y, direction) =>
//            val position = x.flatMap(x => y.map(y => Position(x, y)))
//
//            createEntity(
//                id = id,
//                entityName = entityName,
//                timestamp = timestamp,
//                state = state,
//                position = position,
//                direction = direction
//            )
//    }
//
//    private def convertToEntry(entity: Entity): EntityEntry = {
//        EntityEntry(
//            id = entity.id,
//            entityName = entity.entityName,
//            timestamp = entity.timestamp,
//            state = entity.state,
//            x = entity.position.map(_.x),
//            y = entity.position.map(_.y),
//            direction = entity.direction
//        )
//    }
