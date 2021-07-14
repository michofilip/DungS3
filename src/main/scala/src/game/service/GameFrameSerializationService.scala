package src.game.service

import src.game.entity.EntityRepository
import src.game.event.EventXmlService
import src.game.GameFrame

import scala.util.Try
import scala.xml.Node

class GameFrameSerializationService(gameStateSerializationService: GameStateSerializationService):

    def toXml(gameFrame: GameFrame): Node =
        <GameFrame>
            {gameStateSerializationService.toXml(gameFrame.gameState)}
            <events>
                {gameFrame.events.map(EventXmlService.toXml)}
            </events>
        </GameFrame>

    def fromXml(xml: Node): Option[GameFrame] = Try {
        val gameState = (xml \ "GameState").headOption.flatMap(gameStateSerializationService.fromXml)
        val events = (xml \ "events" \ "Event").flatMap(EventXmlService.fromXml)

        gameState.map { gameState =>
            GameFrame(gameState = gameState, events = events.toVector)
        }
    }.toOption.flatten
