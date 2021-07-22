package src.game.service

import src.game.GameState

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import scala.xml.{PrettyPrinter, XML}

class GameStateFileService(gameStateSerializationService: GameStateSerializationService):

    def loadFromFile(file: File): Option[GameState] =
        val xml = XML.loadFile(file)
        gameStateSerializationService.fromXml(xml)

    def saveToFile(file: File, gameState: GameState): Unit =
        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        val prettyPrinter = new PrettyPrinter(80, 4)

        val xml = gameStateSerializationService.toXml(gameState)

        printWriter.println(prettyPrinter.format(xml))
        printWriter.close()
        