package dod.data.repository


import scalafx.scene.image.{Image, WritableImage}
import dod.data.Resources
import dod.data.model.{FrameEntity, PhysicsEntity, SpriteEntity}
import dod.exception.FailedToExtractImage
import dod.game.gameobject.parts.graphics.Frame
import dod.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class FrameRepository private(spriteRepository: SpriteRepository, tileSetRepository: TileSetRepository) extends Repository[Int, Frame] :

    override protected val dataById: Map[Int, Frame] =
        def frameFrom(frameEntity: FrameEntity): Try[Frame] =
            for
                spriteEntity <- spriteRepository.findById(frameEntity.spriteId).toTry {
                    new NoSuchElementException(s"SpriteEntity id: ${frameEntity.spriteId} not found!")
                }
                tileSet <- tileSetRepository.findById(spriteEntity.fileName).toTry {
                    new NoSuchElementException(s"TileSet id: ${spriteEntity.fileName} not found!")
                }
                sprite <- extractTile(tileSet, spriteEntity).toTry {
                    new FailedToExtractImage()
                }
            yield
                Frame(sprite = sprite, offsetX = frameEntity.offsetX, offsetY = frameEntity.offsetY)

        def extractTile(tileSet: Image, spriteEntity: SpriteEntity): Option[Image] =
            tileSet.pixelReader.map { pixelReader =>
                new WritableImage(
                    pixelReader,
                    spriteEntity.positionX * spriteEntity.baseSize, spriteEntity.positionY * spriteEntity.baseSize,
                    spriteEntity.width * spriteEntity.baseSize, spriteEntity.height * spriteEntity.baseSize
                )
            }

        val xml = XML.load(Resources.frames.reader())

        (xml \ "Frame").map { node =>
            for
                frameEntity <- FrameEntity.fromXML(node)
                frame <- frameFrom(frameEntity)
            yield
                frameEntity.id -> frame
        }.toTrySeq.map(_.toMap).get

object FrameRepository:

    private lazy val frameRepository = new FrameRepository(SpriteRepository(), TileSetRepository())

    def apply(): FrameRepository = frameRepository