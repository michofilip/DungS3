package src.data.repository

import src.data.Resources
import src.data.model.PhysicsEntry
import src.game.entity.parts.physics.Physics

import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsRepository private() extends Repository[Int, Physics] :

    override protected val dataById: Map[Int, Physics] =
        def mapEntryFrom(physicsEntry: PhysicsEntry): Try[(Int, Physics)] = Success {
            physicsEntry.id -> Physics(solid = physicsEntry.solid, opaque = physicsEntry.opaque)
        }

        val xml = XML.load(Resources.physics.reader())

        (xml \ "Physics")
            .map { node =>
                PhysicsEntry.fromXML(node)
                    .flatMap(mapEntryFrom)
            }
            .map {
                case Failure(e) => throw e
                case Success(mapEntry) => mapEntry
            }.toMap

object PhysicsRepository:

    private lazy val physicsRepository = new PhysicsRepository()

    def apply(): PhysicsRepository = physicsRepository