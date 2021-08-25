package src.data.model

import src.exception.FailedToReadObject

import scala.util.{Failure, Try}
import scala.xml.Node

case class SpriteEntry(fileName: String, baseSize: Int, positionX: Int, positionY: Int, width: Int, height: Int):
    def id: String = s"$fileName-$positionX-$positionY"

object SpriteEntry:

    def fromXML(xml: Node): Try[SpriteEntry] = {
        for
            fileName <- Try((xml \ "fileName").map(_.text.trim).head)
            baseSize <- Try((xml \ "baseSize").map(_.text.trim).map(_.toInt).head)
            positionX <- Try((xml \ "positionX").map(_.text.trim).map(_.toInt).head)
            positionY <- Try((xml \ "positionY").map(_.text.trim).map(_.toInt).head)
            width <- Try((xml \ "width").map(_.text.trim).map(_.toInt).head)
            height <- Try((xml \ "height").map(_.text.trim).map(_.toInt).head)
        yield
            SpriteEntry(fileName, baseSize, positionX, positionY, width, height)
    }.recoverWith {
        case e => Failure(new FailedToReadObject("SpriteEntry", e.getMessage))
    }
