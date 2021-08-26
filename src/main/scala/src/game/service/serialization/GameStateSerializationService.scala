package src.game.service.serialization

import src.data.model.GameObjectEntity
import src.exception.FailedToReadObject
import src.game.GameState
import src.game.gameobject.GameObjectRepository
import src.game.service.serialization.{EventSerializationService, GameObjectSerializationService}
import src.game.temporal.Timer
import src.utils.TryUtils.*

import scala.collection.immutable.Queue
import scala.util.{Failure, Try}
import scala.xml.Node

class GameStateSerializationService private(gameObjectSerializationService: GameObjectSerializationService):

    def toXml(gameState: GameState): Node =
        <GameState>
            {TimerSerializationService.toXml(gameState.timer)}
            <gameObjects>
                {gameState.gameObjects.findAll.map(gameObjectSerializationService.toXml)}
            </gameObjects>
            <events>
                {gameState.events.map(EventSerializationService.toXml)}
            </events>
        </GameState>

    def fromXml(xml: Node): Try[GameState] = {
        for
            timer <- Try((xml \ "Timer").map(TimerSerializationService.fromXml).head).flatten
            gameObjects <- (xml \ "gameObjects" \ "GameObject").map(gameObjectSerializationService.fromXml).toTrySeq
            events <- (xml \ "events" \ "Event").map(EventSerializationService.fromXml).toTrySeq
        yield
            GameState(
                timer = timer,
                gameObjects = GameObjectRepository(gameObjects),
                events = Queue(events: _*)
            )
    }.recoverWith {
        case e => Failure(new FailedToReadObject("GameState", e.getMessage))
    }

object GameStateSerializationService:

    private lazy val gameStateSerializationService = new GameStateSerializationService(GameObjectSerializationService())

    def apply(): GameStateSerializationService = gameStateSerializationService