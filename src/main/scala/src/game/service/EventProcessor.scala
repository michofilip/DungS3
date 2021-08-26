package src.game.service

import src.data.model.GameObjectEntry
import src.game.GameState
import src.game.event.Event
import src.game.event.Event.*
import src.game.gameobject.mapper.PositionMapper
import src.game.service.EventProcessor.handleMove
import src.game.service.GameObjectConverter

import java.util.UUID

class EventProcessor private(gameObjectConverter: GameObjectConverter):

    def processEvent(event: Event, gameState: GameState): GameState = event match
        case MoveTo(gameObjectId, x, y) =>
            handleMove(gameObjectId, gameState, PositionMapper.MoveTo(x, y))

        case MoveBy(gameObjectId, dx, dy) =>
            handleMove(gameObjectId, gameState, PositionMapper.MoveBy(dx, dy))

        case StartTimer =>
            gameState.updated(timer = gameState.timer.started)

        case StopTimer =>
            gameState.updated(timer = gameState.timer.stopped)

        case Despawn(gameObjectIds) =>
            val gameObjects = gameObjectIds.flatMap(gameState.gameObjects.findById)
            gameState.updated(gameObjects = gameState.gameObjects -- gameObjects)

        case Spawn(useCurrentTimestamp, gameObjectEntries, events) =>
            val newGameObjects = gameObjectEntries.map { gameObjectEntry =>
                if useCurrentTimestamp then
                    val gameStateTimestamp = gameState.timer.timestamp.milliseconds

                    gameObjectEntry.copy(
                        creationTimestamp = gameStateTimestamp,
                        stateTimestamp = gameObjectEntry.stateTimestamp.map(_ => gameStateTimestamp),
                        positionTimestamp = gameObjectEntry.positionTimestamp.map(_ => gameStateTimestamp)
                    )
                else
                    gameObjectEntry
            }.flatMap { gameObjectEntry =>
                // TODO log if failed
                gameObjectConverter.fromEntry(gameObjectEntry).toOption
            }

            gameState.updated(
                gameObjects = gameState.gameObjects ++ newGameObjects,
                events = gameState.events ++ events
            )


object EventProcessor:

    private lazy val eventProcessor = new EventProcessor(GameObjectConverter())

    def apply(): EventProcessor = eventProcessor

    private def handleMove(gameObjectId: UUID, gameState: GameState, positionMapper: PositionMapper): GameState =
        gameState.gameObjects.findById(gameObjectId)
            .filter(gameObject => gameObject.hasPosition)
            .fold(gameState) { gameObject =>
                val updatedGameObject = gameObject.updatedPosition(positionMapper = positionMapper, timestamp = gameState.timer.timestamp)
                val isSolidAtTarget = updatedGameObject.position.exists(gameState.gameObjects.existSolidAtPosition)

                if !isSolidAtTarget then
                    val newGameObjects = gameState.gameObjects - gameObject + updatedGameObject

                    gameState.updated(gameObjects = newGameObjects)
                else
                    gameState
            }