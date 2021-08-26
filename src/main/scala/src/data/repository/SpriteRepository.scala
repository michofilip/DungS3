package src.data.repository

import src.data.Resources
import src.data.model.{PhysicsEntity, SpriteEntity}
import src.game.gameobject.parts.physics.Physics
import src.utils.TryUtils.*

import java.io.FileInputStream
import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class SpriteRepository private() extends Repository[String, SpriteEntity] :

    override protected val dataById: Map[String, SpriteEntity] =
        val xml = XML.load(new FileInputStream(Resources.sprites))

        (xml \ "Sprite").map { node =>
            for
                spriteEntity <- SpriteEntity.fromXML(node)
            yield
                spriteEntity.id -> spriteEntity
        }.toTrySeq.map(_.toMap).get

object SpriteRepository:

    lazy val spriteRepository: SpriteRepository = new SpriteRepository()

    def apply(): SpriteRepository = spriteRepository