package src.game.service.serialization

import src.data.model.EntityEntry
import src.exception.FailedToReadObject
import src.game.GameState
import src.game.entity.EntityRepository
import src.game.service.serialization.{EntitySerializationService, EventSerializationService}
import src.game.temporal.Timer
import src.utils.TryUtils.*

import scala.collection.immutable.Queue
import scala.util.{Failure, Try}
import scala.xml.Node

class GameStateSerializationService private(entitySerializationService: EntitySerializationService):

    def toXml(gameState: GameState): Node =
        <GameState>
            {TimerSerializationService.toXml(gameState.timer)}
            <entities>
                {gameState.entities.findAll.map(entitySerializationService.toXml)}
            </entities>
            <events>
                {gameState.events.map(EventSerializationService.toXml)}
            </events>
        </GameState>

    def fromXml(xml: Node): Try[GameState] = {
        for
            timer <- Try((xml \ "Timer").map(TimerSerializationService.fromXml).head).flatten
            entities <- (xml \ "entities" \ "Entity").map(entitySerializationService.fromXml).invertTry
            events <- (xml \ "events" \ "Event").map(EventSerializationService.fromXml).invertTry
        yield
            GameState(
                timer = timer,
                entities = EntityRepository(entities),
                events = Queue(events: _*)
            )
    }.recoverWith {
        case e => Failure(new FailedToReadObject("GameState", e.getMessage))
    }

object GameStateSerializationService:

    private lazy val gameStateSerializationService = new GameStateSerializationService(EntitySerializationService())

    def apply(): GameStateSerializationService = gameStateSerializationService