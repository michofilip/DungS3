package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{PhysicsEntry, PhysicsSelectorEntry, PhysicsSelectorV2Entry}
import src.game.entity.selector.PhysicsSelector

import scala.util.Try
import scala.xml.{NodeSeq, XML}

class PhysicsSelectorRepository(using physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    protected val dataById: Map[Int, PhysicsSelector] =
        def convertToPhysicsSelector(physicsSelectorEntry: PhysicsSelectorV2Entry): PhysicsSelector =
            val physics = for {
                variant <- physicsSelectorEntry.variants
                physics <- physicsRepository.findById(variant.physicsId)
            } yield {
                variant.state -> physics
            }

            PhysicsSelector(physics)

        val xml = XML.loadFile(Resources.physicsSelectorsFile)

        (xml \ "PhysicsSelector")
            .flatMap(PhysicsSelectorV2Entry.fromXML)
            .map(physicsSelectorEntry => physicsSelectorEntry.id -> convertToPhysicsSelector(physicsSelectorEntry))
            .toMap
