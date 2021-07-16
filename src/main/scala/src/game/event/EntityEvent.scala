package src.game.event

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.Entity
import src.game.event.Event.EventResponse

import java.util.UUID

object EntityEvent:

    final case class Spawn(entity: Entity) extends Event :
        override def applyTo(gameState: GameState): EventResponse =
            val newEntities = gameState.entities + entity
            val newGameState = gameState.updated(entities = newEntities)
            (newGameState, Vector.empty)

    final case class Kill(entityId: UUID) extends Event :
        override def applyTo(gameState: GameState): EventResponse =
            val newEntities = gameState.entities.findById(entityId).fold(gameState.entities) { entity =>
                gameState.entities - entity
            }
            val newGameState = gameState.updated(entities = newEntities)
            (newGameState, Vector.empty)
