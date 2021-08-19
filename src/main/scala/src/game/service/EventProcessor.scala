package src.game.service

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.mapper.PositionMapper
import src.game.event.Event
import src.game.event.Event.*
import src.game.service.EntityConverter
import src.game.service.EventProcessor.handleMove

import java.util.UUID

class EventProcessor private(entityConverter: EntityConverter):

    def processEvent(event: Event, gameState: GameState): GameState = event match 
        case MoveTo(entityId, x, y) =>
            handleMove(entityId, gameState, PositionMapper.MoveTo(x, y))

        case MoveBy(entityId, dx, dy) =>
            handleMove(entityId, gameState, PositionMapper.MoveBy(dx, dy))

        case StartTimer =>
            gameState.updated(timer = gameState.timer.started)

        case StopTimer =>
            gameState.updated(timer = gameState.timer.stopped)

        case Despawn(entityIds) =>
            val entities = entityIds.flatMap(gameState.entities.findById)
            gameState.updated(entities = gameState.entities -- entities)

        case Spawn(useCurrentTimestamp, entities, events) =>
            val newEntities = entities.map { entityEntry =>
                if useCurrentTimestamp then
                    val gameStateTimestamp = gameState.timer.timestamp.milliseconds

                    entityEntry.copy(
                        creationTimestamp = gameStateTimestamp,
                        stateTimestamp = entityEntry.stateTimestamp.map(_ => gameStateTimestamp),
                        positionTimestamp = entityEntry.positionTimestamp.map(_ => gameStateTimestamp)
                    )
                else
                    entityEntry
            }.flatMap { entityEntry =>
                // TODO log if failed
                entityConverter.convertToEntity(entityEntry).toOption
            }

            gameState.updated(
                entities = gameState.entities ++ newEntities,
                events = gameState.events ++ events
            )


object EventProcessor:

    private lazy val eventProcessor = new EventProcessor(EntityConverter())

    def apply(): EventProcessor = eventProcessor

    private def handleMove(entityId: UUID, gameState: GameState, positionMapper: PositionMapper): GameState =
        gameState.entities.findById(entityId)
            .filter(entity => entity.hasPosition)
            .fold(gameState) { entity =>
                val updatedEntity = entity.updatedPosition(positionMapper = positionMapper, timestamp = gameState.timer.timestamp)
                val isSolidAtTarget = updatedEntity.position.exists(gameState.entities.existSolidAtPosition)

                if !isSolidAtTarget then
                    val newEntities = gameState.entities - entity + updatedEntity

                    gameState.updated(entities = newEntities)
                else
                    gameState
            }