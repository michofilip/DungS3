package src.game.service

import src.data.model.EntityEntry
import src.game.GameState
import src.game.entity.EntityRepository
import src.game.entity.parts.state.State
import src.game.temporal.Timer

import java.io.File
import java.util.UUID
import scala.collection.immutable.Queue
import scala.io.{BufferedSource, Source}
import scala.util.Try

class GameStateBuilder private(entityConverter: EntityConverter):

    def load(mapName: String): Option[GameState] = Try {
        val map: BufferedSource = Source.fromResource(s"maps/$mapName.lvl")
        val chars: Vector[Vector[Char]] = map.getLines().toVector.map(line => line.toVector)

        val entities = {
            for {
                x <- 0 until 64
                y <- 0 until 64
            } yield {
                mapChar(x, y, chars(y)(2 * x))
            }
        }
            .flatten
            .flatMap(entityConverter.convertToEntity)

        GameState(
            timer = Timer(),
            entities = EntityRepository(entities),
            events = Queue.empty
        )
    }.toOption

    private def mapChar(x: Int, y: Int, char: Char): Seq[EntityEntry] = {
        def makeFloor(x: Int, y: Int): EntityEntry =
            EntityEntry(
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

        def makeWall(x: Int, y: Int): EntityEntry =
            EntityEntry(
                id = UUID.randomUUID().toString,
                name = "wall",
                creationTimestamp = 0L,
                state = None,
                stateTimestamp = Some(0L),
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L))

        def makeDoor(x: Int, y: Int): EntityEntry =
            EntityEntry(
                id = UUID.randomUUID().toString,
                name = "door",
                creationTimestamp = 0L,
                state = Option(State.Closed),
                stateTimestamp = Some(0L),
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L))

        def makePlayer(x: Int, y: Int): EntityEntry =
            EntityEntry(
                id = UUID.randomUUID().toString,
                name = "player",
                creationTimestamp = 0L,
                state = None,
                stateTimestamp = Some(0L),
                x = Option(x),
                y = Option(y),
                direction = None,
                positionTimestamp = Some(0L))

        char match {
            case ' ' => Seq.empty
            case '.' => Seq(makeFloor(x, y))
            case '#' => Seq(makeFloor(x, y), makeWall(x, y))
            case '+' => Seq(makeFloor(x, y), makeDoor(x, y))
            case '@' => Seq(makeFloor(x, y), makePlayer(x, y))
            case _ => Seq(makeFloor(x, y))
        }
    }

object GameStateBuilder:

    private lazy val gameStateBuilder = new GameStateBuilder(EntityConverter())

    def apply(): GameStateBuilder = gameStateBuilder