package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.PhysicsSelectorEntry
import src.game.entity.selector.PhysicsSelector

class PhysicsSelectorRepository(using physicsRepository: PhysicsRepository) extends Repository[String, PhysicsSelector] :

    override protected val dataById: Map[String, PhysicsSelector] =
        def convertToPhysicsSelector(physicsSelectorEntries: Seq[PhysicsSelectorEntry]): PhysicsSelector =
            val physics = for {
                physicsSelectorEntry <- physicsSelectorEntries
                animation <- physicsRepository.findById(physicsSelectorEntry.physicsId)
            } yield {
                physicsSelectorEntry.state -> animation
            }

            PhysicsSelector(physics: _*)

        FileReader.readFile(Resources.physicsSelectorEntriesFile, PhysicsSelectorEntry.reader)
            .groupBy(_.name)
            .view
            .mapValues(convertToPhysicsSelector)
            .toMap
