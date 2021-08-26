package src.game.service

import src.data.model.GameObjectEntry
import src.game.GameState
import src.game.gameobject.GameObjectRepository
import src.game.gameobject.parts.state.State
import src.game.temporal.Timer

import java.io.File
import java.util.UUID
import scala.collection.immutable.Queue
import scala.io.{BufferedSource, Source}
import scala.util.Try

class GameStateBuilder private(gameObjectConverter: GameObjectConverter):

    def load(mapName: String): Option[GameState] = Try {
        val map: BufferedSource = Source.fromResource(s"maps/$mapName.lvl")
        val chars: Vector[Vector[Char]] = map.getLines().toVector.map(line => line.toVector)

        val gameObjects = {
            for {
                x <- 0 until 64
                y <- 0 until 64
            } yield {
                mapChar(x, y, chars(y)(2 * x))
            }
        }.flatten.flatMap { gameObjectEntry =>
            // TODO log if failed
            gameObjectConverter.fromEntry(gameObjectEntry).toOption
        }

        GameState(
            timer = Timer(),
            gameObjects = GameObjectRepository(gameObjects),
            events = Queue.empty
        )
    }.toOption

    private def mapChar(x: Int, y: Int, char: Char): Seq[GameObjectEntry] = {
        def makeFloor(x: Int, y: Int): GameObjectEntry =
            GameObjectEntry(
                id = UUID.randomUUID().toString,
                name = "floor",
                creationTimestamp = 0L,
                state = None,
                stateTimestamp = Some(0L),
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L)
            )

        def makeWall(x: Int, y: Int): GameObjectEntry =
            GameObjectEntry(
                id = UUID.randomUUID().toString,
                name = "wall",
                creationTimestamp = 0L,
                state = None,
                stateTimestamp = None,
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L)
            )

        def makeDoor(x: Int, y: Int): GameObjectEntry =
            GameObjectEntry(
                id = UUID.randomUUID().toString,
                name = "door",
                creationTimestamp = 0L,
                state = Option(State.Closed),
                stateTimestamp = Some(0L),
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L)
            )

        def makePlayer(x: Int, y: Int): GameObjectEntry =
            GameObjectEntry(
                id = UUID.randomUUID().toString,
                name = "player",
                creationTimestamp = 0L,
                state = None,
                stateTimestamp = None,
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L)
            )

        char match
            case ' ' => Seq.empty
            case '.' => Seq(makeFloor(x, y))
            case '#' => Seq(makeFloor(x, y), makeWall(x, y))
            case '+' => Seq(makeFloor(x, y), makeDoor(x, y))
            case '@' => Seq(makeFloor(x, y), makePlayer(x, y))
            case _ => Seq(makeFloor(x, y))

    }

object GameStateBuilder:

    private lazy val gameStateBuilder = new GameStateBuilder(GameObjectConverter())

    def apply(): GameStateBuilder = gameStateBuilder