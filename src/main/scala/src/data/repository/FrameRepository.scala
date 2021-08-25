package src.data.repository


import scalafx.scene.image.{Image, WritableImage}
import src.data.Resources
import src.data.model.{FrameEntry, PhysicsEntry, SpriteEntry}
import src.exception.FailedToExtractImage
import src.game.entity.parts.graphics.Frame
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class FrameRepository private(spriteRepository: SpriteRepository, tileSetRepository: TileSetRepository) extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def frameFrom(frameEntry: FrameEntry): Try[Frame] =
            for
                spriteEntry <- spriteRepository.findById(frameEntry.spriteId).toTry(new NoSuchElementException(s"SpriteEntry id: ${frameEntry.spriteId} not found!"))
                tileSet <- tileSetRepository.findById(spriteEntry.fileName).toTry(new NoSuchElementException(s"TileSet id: ${spriteEntry.fileName} not found!"))
                sprite <- extractTile(tileSet, spriteEntry).toTry(new FailedToExtractImage())
            yield
                Frame(sprite = sprite, offsetX = frameEntry.offsetX, offsetY = frameEntry.offsetY)
                
        def extractTile(tileSet: Image, spriteEntry: SpriteEntry): Option[Image] =
            tileSet.pixelReader.map { pixelReader =>
                new WritableImage(
                    pixelReader,
                    spriteEntry.positionX * spriteEntry.baseSize, spriteEntry.positionY * spriteEntry.baseSize,
                    spriteEntry.width * spriteEntry.baseSize, spriteEntry.height * spriteEntry.baseSize
                )
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

    private lazy val frameRepository = new FrameRepository(SpriteRepository(), TileSetRepository())

    def apply(): FrameRepository = frameRepository