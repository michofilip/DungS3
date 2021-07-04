package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{PhysicsEntry, PhysicsSelectorEntry, PhysicsSelectorV2Entry}
import src.game.entity.selector.PhysicsSelector

import scala.util.Try
import scala.xml.{NodeSeq, XML}

class PhysicsSelectorRepository(using physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    //    override protected val dataById: Map[Int, PhysicsSelector] =
    //        def convertToPhysicsSelector(physicsSelectorEntries: Seq[PhysicsSelectorEntry]): PhysicsSelector =
    //            val physics = for {
    //                physicsSelectorEntry <- physicsSelectorEntries
    //                physics <- physicsRepository.findById(physicsSelectorEntry.physicsId)
    //            } yield {
    //                physicsSelectorEntry.state -> physics
    //            }
    //
    //            PhysicsSelector(physics)
    //
    //        FileReader.readFile(Resources.physicsSelectorEntriesFile, PhysicsSelectorEntry.reader)
    //            .groupBy(_.id)
    //            .view
    //            .mapValues(convertToPhysicsSelector)
    //            .toMap

    protected val dataById: Map[Int, PhysicsSelector] =
        def convertToPhysicsSelector(physicsSelectorEntry: PhysicsSelectorV2Entry): PhysicsSelector =
            val physics = for {
                singlePhysicsSelectorEntry <- physicsSelectorEntry.singlePhysicsSelectorEntries
                physics <- physicsRepository.findById(singlePhysicsSelectorEntry.physicsId)
            } yield {
                singlePhysicsSelectorEntry.state -> physics
            }

            PhysicsSelector(physics)

        val xml = XML.loadFile(Resources.physicsSelectorEntriesXmlFile)

        (xml \ "physics-selector-entry")
            .flatMap(PhysicsSelectorV2Entry.fromXML)
            .map(physicsSelectorEntry => physicsSelectorEntry.id -> convertToPhysicsSelector(physicsSelectorEntry))
            .toMap
        