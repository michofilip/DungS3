package src.data.repository

import src.data.Resources
import src.data.model.{FrameEntry, PhysicsEntry}
import src.game.entity.parts.graphics.Frame
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class FrameRepository private(spriteRepository: SpriteRepository) extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def frameFrom(frameEntry: FrameEntry): Try[Frame] =
            spriteRepository.findById(frameEntry.spriteId).map { sprite =>
                Success {
                    Frame(sprite = sprite, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)
                }
            }.getOrElse {
                Failure {
                    new NoSuchElementException(s"Sprite id: ${frameEntry.spriteId} not found!")
                }
            }

        val xml = XML.load(Resources.frames.reader())

        (xml \ "Frame").map { node =>
            for
                frameEntry <- FrameEntry.fromXML(node)
                frame <- frameFrom(frameEntry)
            yield
                frameEntry.id -> frame
        }.invertTry.map(_.toMap).get

object FrameRepository:

    private lazy val frameRepository = new FrameRepository(SpriteRepository())

    def apply(): FrameRepository = frameRepository