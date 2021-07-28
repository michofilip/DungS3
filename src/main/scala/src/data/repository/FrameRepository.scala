package src.data.repository

import src.data.Resources
import src.data.model.{FrameEntry, PhysicsEntry}
import src.game.entity.parts.graphics.Frame

import scala.xml.XML

final class FrameRepository private() extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def convertToFrame(frameEntry: FrameEntry): Frame =
            Frame(imageId = frameEntry.imageId, layer = frameEntry.layer, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)

        val xml = XML.load(Resources.frames.reader())

        (xml \ "Frame")
            .flatMap(FrameEntry.fromXML)
            .map(frameEntry => frameEntry.id -> convertToFrame(frameEntry))
            .toMap

object FrameRepository:
    
    private lazy val frameRepository = new FrameRepository()

    def apply(): FrameRepository = frameRepository