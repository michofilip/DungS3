package src.game.service

import src.game.GameState
import src.game.service.serialization.GameStateSerializationService

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import scala.xml.{PrettyPrinter, XML}

class GameStateFileProcessor private(gameStateSerializationService: GameStateSerializationService):

    def loadFromFile(file: File): Option[GameState] =
        val xml = XML.loadFile(file)
        // TODO fix it
        gameStateSerializationService.fromXml(xml).toOption

    def saveToFile(file: File, gameState: GameState): Unit =
        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        val prettyPrinter = new PrettyPrinter(80, 4)

        val xml = gameStateSerializationService.toXml(gameState)

        printWriter.println(prettyPrinter.format(xml))
        printWriter.close()

object GameStateFileProcessor:

    private lazy val gameStateFileProcessor = new GameStateFileProcessor(GameStateSerializationService())

    def apply(): GameStateFileProcessor = gameStateFileProcessor