package src.game

import src.game.entity.Entity

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}
import scala.xml.{PrettyPrinter, XML}

class GameFrameFileService(gameFrameXmlService: GameFrameXmlService):

    def loadFromFile(file: File): Option[GameFrame] =
        val xml = XML.loadFile(file)
        gameFrameXmlService.fromXml(xml)

    def saveToFile(file: File, gameFrame: GameFrame): Unit =
        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        val prettyPrinter = new PrettyPrinter(80, 4)

        val xml = gameFrameXmlService.toXml(gameFrame)

        printWriter.println(prettyPrinter.format(xml))
        printWriter.close()