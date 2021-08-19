package src.data.repository

import src.data.Resources
import src.data.model.PhysicsEntry
import src.game.entity.parts.physics.Physics
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsRepository private() extends Repository[Int, Physics] :

    override protected val dataById: Map[Int, Physics] =
        def physicsFrom(physicsEntry: PhysicsEntry): Try[Physics] = Success {
            Physics(solid = physicsEntry.solid, opaque = physicsEntry.opaque)
        }

        val xml = XML.load(Resources.physics.reader())

        (xml \ "Physics").map { node =>
            for
                physicsEntry <- PhysicsEntry.fromXML(node)
                physics <- physicsFrom(physicsEntry)
            yield
                physicsEntry.id -> physics
        }.invertTry.map(_.toMap).get

object PhysicsRepository:

    private lazy val physicsRepository = new PhysicsRepository()

    def apply(): PhysicsRepository = physicsRepository