package src.entity

import src.entity.parts.{Direction, Position, State}
import src.entity.repository.EntityPrototypeRepository

import java.util.UUID

class EntityService(entityPrototypeRepository: EntityPrototypeRepository):

    def createEntity(id: UUID,
                     name: String,
                     state: Option[State] = None,
                     position: Option[Position] = None,
                     direction: Option[Direction] = None): Option[Entity] =
        entityPrototypeRepository.findByName(name).map { entityPrototype =>
            val validState = entityPrototype.getValidatedState(state)
            val validPosition = entityPrototype.getValidatedPosition(position)
            val validDirection = entityPrototype.getValidatedDirection(direction)
            val physicsSelector = entityPrototype.physicsSelector
            val graphicsSelector = entityPrototype.graphicsSelector

            Entity(
                id = id,
                name = name,
                state = validState,
                position = validPosition,
                direction = validDirection,
                physicsSelector = physicsSelector,
                graphicsSelector = graphicsSelector
            )
        }
