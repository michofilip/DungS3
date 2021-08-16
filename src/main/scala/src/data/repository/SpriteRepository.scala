package src.data.repository

import scalafx.scene.image.{Image, WritableImage}
import src.data.Resources
import src.utils.StringUtils.*

import java.io.FileInputStream

final class SpriteRepository private() extends Repository[String, Image] :
    override protected val dataById: Map[String, Image] =
        val spritesFile = Resources.tiles
        val spritesFileName = spritesFile.getName.removeFileExtension

        val sprites = new Image(new FileInputStream(spritesFile))

        val tileSize = 32

        val width = (sprites.width / tileSize).toInt
        val height = (sprites.height / tileSize).toInt

        sprites.pixelReader.fold(Seq.empty) { pixelReader =>
            for
                x <- 0 until width
                y <- 0 until height
            yield
                s"$spritesFileName-$x-$y" -> new WritableImage(pixelReader, x * tileSize, y * tileSize, tileSize, tileSize)
        }.toMap

object SpriteRepository:

    lazy val spriteRepository: SpriteRepository = new SpriteRepository()

    def apply(): SpriteRepository = spriteRepository
