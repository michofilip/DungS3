package src.game.service

import src.game.GameFrame
import src.game.entity.Entity

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import scala.xml.{PrettyPrinter, XML}

class GameFrameFileService(gameFrameSerializationService: GameFrameSerializationService):

    def loadFromFile(file: File): Option[GameFrame] =
        val xml = XML.loadFile(file)
        gameFrameSerializationService.fromXml(xml)

    def saveToFile(file: File, gameFrame: GameFrame): Unit =
        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        val prettyPrinter = new PrettyPrinter(80, 4)

        val xml = gameFrameSerializationService.toXml(gameFrame)

        printWriter.println(prettyPrinter.format(xml))
        printWriter.close()