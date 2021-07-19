//package src.game.event
//
//import src.game.entity.mapper.PositionMapper
//import src.game.GameState
//import src.game.event.Event.EventResponse
//
//import java.util.UUID
//
//object PositionEvent:
//
//    private def handleMove(entityId: UUID, gameState: GameState, positionMapper: PositionMapper): EventResponse =
//        gameState.entities.findById(entityId)
//            .filter(entity => entity.hasPosition)
//            .fold((gameState, Vector.empty)) { entity =>
//                val updatedEntity = entity.updated(position = positionMapper, timestamp = gameState.timer.timestamp)
//                val isSolidAtTarget = updatedEntity.position.exists(gameState.entities.existSolidAtPosition)
//
//                if !isSolidAtTarget then
//                    val newEntities = gameState.entities - entity + updatedEntity
//
//                    (gameState.updated(entities = newEntities), Vector.empty)
//                else
//                    (gameState, Vector.empty)
//            }
//
//    final case class MoveTo(entityId: UUID, x: Int, y: Int) extends Event :
//        override def applyTo(gameState: GameState): EventResponse =
//            handleMove(entityId, gameState, PositionMapper.MoveTo(x, y))
//
//    final case class MoveBy(entityId: UUID, dx: Int, dy: Int) extends Event :
//        override def applyTo(gameState: GameState): EventResponse =
//            handleMove(entityId, gameState, PositionMapper.MoveBy(dx, dy))
