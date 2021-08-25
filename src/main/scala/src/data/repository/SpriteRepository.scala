package src.data.repository

import src.data.Resources
import src.data.model.{PhysicsEntry, SpriteEntry}
import src.game.entity.parts.physics.Physics
import src.utils.TryUtils.*

import java.io.FileInputStream
import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class SpriteRepository private() extends Repository[String, SpriteEntry] :

    override protected val dataById: Map[String, SpriteEntry] =
        val xml = XML.load(new FileInputStream(Resources.sprites))

        (xml \ "Sprite").map { node =>
            for
                spriteEntry <- SpriteEntry.fromXML(node)
            yield
                spriteEntry.id -> spriteEntry
        }.invertTry.map(_.toMap).get

object SpriteRepository:

    lazy val spriteRepository: SpriteRepository = new SpriteRepository()

    def apply(): SpriteRepository = spriteRepository