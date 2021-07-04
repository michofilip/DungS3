package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{FrameEntry, PhysicsEntry}
import src.game.entity.parts.animation.Frame

import scala.xml.XML

final class FrameRepository extends Repository[Int, Frame] :

    //    override protected val dataById: Map[Int, Frame] =
    //        def convertToFrame(frameEntry: FrameEntry): Frame =
    //            Frame(imageId = frameEntry.imageId, layer = frameEntry.layer, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)
    //
    //        FileReader.readFile(Resources.frameEntriesFile, FrameEntry.reader)
    //            .map(frameEntry => frameEntry.id -> convertToFrame(frameEntry))
    //            .toMap

    override protected val dataById: Map[Int, Frame] =
        def convertToFrame(frameEntry: FrameEntry): Frame =
            Frame(imageId = frameEntry.imageId, layer = frameEntry.layer, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)

        val xml = XML.loadFile(Resources.frameEntriesXmlFile)

        (xml \ "FrameEntry")
            .flatMap(FrameEntry.fromXML)
            .map(frameEntry => frameEntry.id -> convertToFrame(frameEntry))
            .toMap
