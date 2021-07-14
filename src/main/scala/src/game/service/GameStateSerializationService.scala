package src.game.service

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.{EntityRepository, EntityService}
import src.game.temporal.Timer

import scala.util.Try
import scala.xml.Node

class GameStateSerializationService(entityService: EntityService):

    def toXml(gameState: GameState): Node =
        <GameState>
            {gameState.timer.toXml}
            <entities>
                {gameState.entities.findAll.map(entityService.toXml)}
            </entities>
        </GameState>

    def fromXml(xml: Node): Option[GameState] = Try {
        val timer = (xml \ "Timer").headOption.flatMap(Timer.fromXml)
        val entities = (xml \ "entities" \ "Entity").flatMap(entityService.fromXml)

        timer.map { timer =>
            GameState(timer = timer, entities = EntityRepository(entities))
        }
    }.toOption.flatten
    