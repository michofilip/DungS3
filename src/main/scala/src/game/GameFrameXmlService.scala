package src.game

import src.game.entity.EntityRepository
import src.game.event.EventXmlService

import scala.util.Try
import scala.xml.Node

class GameFrameXmlService(gameStateXmlService: GameStateXmlService):

    def toXml(gameFrame: GameFrame): Node =
        <GameFrame>
            {gameStateXmlService.toXml(gameFrame.gameState)}
            <events>
                {gameFrame.events.map(EventXmlService.toXml)}
            </events>
        </GameFrame>

    def fromXml(xml: Node): Option[GameFrame] = Try {
        val gameState = (xml \ "GameState").headOption.flatMap(gameStateXmlService.fromXml)
        val events = (xml \ "events" \ "Event").flatMap(EventXmlService.fromXml)

        gameState.map { gameState =>
            GameFrame(gameState = gameState, events = events.toVector)
        }
    }.toOption.flatten
