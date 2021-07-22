package src.game.service

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.mapper.PositionMapper
import src.game.event.Event
import src.game.event.Event.*
import src.game.service.EventProcessor.handleMove
import src.game.service.EntityConverter

import java.util.UUID

class EventProcessor(entityConverter: EntityConverter):

    def processEvent(event: Event, gameState: GameState): GameState = event match {
        case MoveTo(entityId, x, y) =>
            handleMove(entityId, gameState, PositionMapper.MoveTo(x, y))

        case MoveBy(entityId, dx, dy) =>
            handleMove(entityId, gameState, PositionMapper.MoveBy(dx, dy))

        case StartTimer =>
            gameState.updated(timer = gameState.timer.started)

        case StopTimer =>
            gameState.updated(timer = gameState.timer.stopped)

        case Kill(entityId) =>
            gameState.entities.findById(entityId).fold(gameState) { entity =>
                gameState.updated(entities = gameState.entities - entity)
            }

        case Spawn(entityEntry: EntityEntry) =>
            entityConverter.convertToEntity(entityEntry).fold(gameState) { entity =>
                gameState.updated(entities = gameState.entities + entity)
            }
    }


object EventProcessor:
    private def handleMove(entityId: UUID, gameState: GameState, positionMapper: PositionMapper): GameState =
        gameState.entities.findById(entityId)
            .filter(entity => entity.hasPosition)
            .fold(gameState) { entity =>
                val updatedEntity = entity.updated(position = positionMapper, timestamp = gameState.timer.timestamp)
                val isSolidAtTarget = updatedEntity.position.exists(gameState.entities.existSolidAtPosition)

                if !isSolidAtTarget then
                    val newEntities = gameState.entities - entity + updatedEntity

                    gameState.updated(entities = newEntities)
                else
                    gameState
            }