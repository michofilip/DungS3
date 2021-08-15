package src.data.repository

import src.data.Resources
import src.data.model.{FrameEntry, PhysicsEntry}
import src.game.entity.parts.graphics.Frame

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class FrameRepository private(spriteRepository: SpriteRepository) extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def mapEntryFrom(frameEntry: FrameEntry): Try[(Int, Frame)] =
            spriteRepository.findById(frameEntry.spriteId) match {
                case None => Failure {
                    new NoSuchElementException(s"Sprite id: ${frameEntry.spriteId} not found!")
                }
                case Some(sprite) => Success {
                    frameEntry.id -> Frame(sprite = sprite, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)
                }
            }

        val xml = XML.load(Resources.frames.reader())

        (xml \ "Frame")
            .map { node =>
                FrameEntry.fromXML(node)
                    .flatMap(mapEntryFrom)
            }
            .map {
                case Failure(e) => throw e
                case Success(mapEntry) => mapEntry
            }.toMap

object FrameRepository:

    private lazy val frameRepository = new FrameRepository(SpriteRepository())

    def apply(): FrameRepository = frameRepository