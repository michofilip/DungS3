package src.game.event2

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.mapper.PositionMapper
import src.game.event2.Event.*
import src.game.event2.EventProcessor.handleMove
import src.game.service.EntityService

import java.util.UUID

class EventProcessor(entityService: EntityService):

    def processEvent(event: Event, gameState: GameState): (GameState, Vector[Event]) = event match {
        case MoveTo(entityId, x, y) =>
            handleMove(entityId, gameState, PositionMapper.MoveTo(x, y))

        case MoveBy(entityId, dx, dy) =>
            handleMove(entityId, gameState, PositionMapper.MoveBy(dx, dy))

        case StartTimer =>
            (gameState.updated(timer = gameState.timer.started), Vector.empty)

        case StopTimer =>
            (gameState.updated(timer = gameState.timer.stopped), Vector.empty)

        case Kill(entityId) =>
            gameState.entities.findById(entityId).fold((gameState, Vector.empty)) { entity =>
                val newEntities = gameState.entities - entity
                val newGameState = gameState.updated(entities = newEntities)
                (newGameState, Vector.empty)
            }

        case Spawn(entityEntry: EntityEntry) =>
            entityService.convertToEntity(entityEntry).fold((gameState, Vector.empty)) { entity =>
                val newEntities = gameState.entities + entity
                val newGameState = gameState.updated(entities = newEntities)
                (newGameState, Vector.empty)
            }
    }


object EventProcessor:
    private def handleMove(entityId: UUID, gameState: GameState, positionMapper: PositionMapper): (GameState, Vector[Event]) =
        gameState.entities.findById(entityId)
            .filter(entity => entity.hasPosition)
            .fold((gameState, Vector.empty)) { entity =>
                val updatedEntity = entity.updated(position = positionMapper, timestamp = gameState.timer.timestamp)
                val isSolidAtTarget = updatedEntity.position.exists(gameState.entities.existSolidAtPosition)

                if !isSolidAtTarget then
                    val newEntities = gameState.entities - entity + updatedEntity

                    (gameState.updated(entities = newEntities), Vector.empty)
                else
                    (gameState, Vector.empty)
            }