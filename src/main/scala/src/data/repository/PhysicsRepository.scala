package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.PhysicsEntry
import src.game.entity.parts.Physics

import scala.util.Try
import scala.xml.{NodeSeq, XML}

class PhysicsRepository extends Repository[Int, Physics] :

    override protected val dataById: Map[Int, Physics] =
        def convertToPhysics(physicsEntry: PhysicsEntry): Physics =
            Physics(solid = physicsEntry.solid, opaque = physicsEntry.opaque)

        val xml = XML.load(Resources.physics.reader())

        (xml \ "Physics")
            .flatMap(PhysicsEntry.fromXML)
            .map(physicsEntry => physicsEntry.id -> convertToPhysics(physicsEntry))
            .toMap
