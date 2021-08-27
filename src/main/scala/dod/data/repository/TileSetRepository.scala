package dod.data.repository

import dod.data.Resources
import dod.utils.FileUtils
import dod.utils.StringUtils.*
import scalafx.scene.image.{Image, WritableImage}

import java.io.FileInputStream

final class TileSetRepository private() extends Repository[String, Image] :
    override protected val dataById: Map[String, Image] =
        FileUtils.filesInDir(Resources.tileSets).map { tileSet =>
            tileSet.getName.removeFileExtension -> new Image(new FileInputStream(tileSet))
        }.toMap

object TileSetRepository:

    lazy val tileSetRepository: TileSetRepository = new TileSetRepository()

    def apply(): TileSetRepository = tileSetRepository
