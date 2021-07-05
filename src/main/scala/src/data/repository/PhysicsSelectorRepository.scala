package src.data.repository

import src.data.Resources
import src.data.model.{PhysicsEntry, PhysicsSelectorEntry}
import src.game.entity.selector.PhysicsSelector

import scala.util.Try
import scala.xml.{NodeSeq, XML}

class PhysicsSelectorRepository(using physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    protected val dataById: Map[Int, PhysicsSelector] =
        def convertToPhysicsSelector(physicsSelectorEntry: PhysicsSelectorEntry): PhysicsSelector =
            val physics = for {
                variant <- physicsSelectorEntry.variants
                physics <- physicsRepository.findById(variant.physicsId)
            } yield {
                variant.state -> physics
            }

            PhysicsSelector(physics)

        val xml = XML.load(Resources.physicsSelectors.reader())

        (xml \ "PhysicsSelector")
            .flatMap(PhysicsSelectorEntry.fromXML)
            .map(physicsSelectorEntry => physicsSelectorEntry.id -> convertToPhysicsSelector(physicsSelectorEntry))
            .toMap
