package src.game

import src.game.entity.EntityRepository
import src.game.event.Event
import src.game.temporal.Timer

import scala.xml.Node

// TODO turn, etc
final class GameState(val timer: Timer, val entities: EntityRepository):

    def updated(timer: Timer = timer, entities: EntityRepository = entities): GameState =
        GameState(
            timer = timer,
            entities = entities
        )

    def toXml:Node=
        <GameState>
            {timer.toXml}
            <entities>
            </entities>
        </GameState>

    override def toString: String = s"GameState(timer=$timer, entities=$entities)"