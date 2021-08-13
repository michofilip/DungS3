package src.data.repository

import src.data.Resources
import src.data.model.{FrameEntry, PhysicsEntry}
import src.game.entity.parts.graphics.Frame

import scala.xml.XML

final class FrameRepository private(spriteRepository: SpriteRepository) extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def convertToFrame(frameEntry: FrameEntry): Option[Frame] =
            for {
                sprite <- spriteRepository.findById(frameEntry.spriteId)
            } yield {
                Frame(sprite = sprite, layer = frameEntry.layer, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)
            }


        val xml = XML.load(Resources.frames.reader())

        (xml \ "Frame")
            .flatMap(FrameEntry.fromXML)
            .map(frameEntry => frameEntry.id -> convertToFrame(frameEntry))
            .collect {
                case (id, Some(frame)) => id -> frame
            }.toMap

object FrameRepository:

    private lazy val frameRepository = new FrameRepository(SpriteRepository())

    def apply(): FrameRepository = frameRepository