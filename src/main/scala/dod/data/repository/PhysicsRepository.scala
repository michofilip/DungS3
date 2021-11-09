package dod.data.repository

import dod.data.Resources
import dod.data.model.PhysicsEntity
import dod.game.gameobject.parts.physics.Physics
import dod.utils.FileUtils
import dod.utils.TryUtils.*

import java.io.FileInputStream
import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsRepository private() extends Repository[Int, Physics] :

    override protected val dataById: Map[Int, Physics] =
        def physicsFrom(physicsEntity: PhysicsEntity): Try[Physics] = Success {
            Physics(solid = physicsEntity.solid, opaque = physicsEntity.opaque)
        }

        FileUtils.filesInDir(Resources.physics).map { file =>
            XML.load(new FileInputStream(file))
        }.flatMap { xml =>
            (xml \ "Physics").map { node =>
                for
                    physicsEntity <- PhysicsEntity.fromXML(node)
                    physics <- physicsFrom(physicsEntity)
                yield
                    physicsEntity.id -> physics
            }
        }.toTrySeq.map(_.toMap).get

object PhysicsRepository:

    private lazy val physicsRepository = new PhysicsRepository()

    def apply(): PhysicsRepository = physicsRepository