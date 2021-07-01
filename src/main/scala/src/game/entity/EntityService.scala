package src.game.entity

import src.data.repository.EntityPrototypeRepository
import src.game.entity.parts.{Direction, Position, State}
import src.game.temporal.Timestamp

import java.util.UUID

class EntityService(entityPrototypeRepository: EntityPrototypeRepository):

    def createEntity(id: UUID,
                     name: String,
                     state: Option[State] = None,
                     position: Option[Position] = None,
                     direction: Option[Direction] = None,
                     timestamp: Timestamp): Option[Entity] =
        entityPrototypeRepository.findByName(name).map { entityPrototype =>
            val validState = entityPrototype.getValidatedState(state)
            val stateTimestamp = if (validState.isDefined) Some(timestamp) else None
            val validPosition = entityPrototype.getValidatedPosition(position)
            val validDirection = entityPrototype.getValidatedDirection(direction)
            val physicsSelector = entityPrototype.physicsSelector
            val graphicsSelector = entityPrototype.graphicsSelector
            val animationSelector = entityPrototype.animationSelector

            Entity(
                id = id,
                name = name,
                state = validState,
                stateTimestamp = stateTimestamp,
                position = validPosition,
                direction = validDirection,
                physicsSelector = physicsSelector,
                graphicsSelector = graphicsSelector,
                animationSelector = animationSelector
            )
        }
