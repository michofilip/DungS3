package src.game

import src.game.entity.EntityRepository
import src.game.event.Event

// TODO time, turn, etc
final class GameState(val entities: EntityRepository):

    def updated(entities: EntityRepository = entities): GameState =
        GameState(
            entities = entities
        )

    override def toString: String = s"GameState(entities=$entities)"