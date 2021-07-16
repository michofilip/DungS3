package src.game.service

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.EntityRepository
import src.game.temporal.Timer

import scala.util.Try
import scala.xml.Node

class GameStateSerializationService(entitySerializationService: EntitySerializationService):

    def toXml(gameState: GameState): Node =
        <GameState>
            {TimerSerializationService.toXml(gameState.timer)}
            <entities>
                {gameState.entities.findAll.map(entitySerializationService.toXml)}
            </entities>
        </GameState>

    def fromXml(xml: Node): Option[GameState] = Try {
        val timer = (xml \ "Timer").headOption.flatMap(TimerSerializationService.fromXml)
        val entities = (xml \ "entities" \ "Entity").flatMap(entitySerializationService.fromXml)

        timer.map { timer =>
            GameState(timer = timer, entities = EntityRepository(entities))
        }
    }.toOption.flatten
    