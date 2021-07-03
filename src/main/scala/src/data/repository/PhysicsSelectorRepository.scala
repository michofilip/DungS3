package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.PhysicsSelectorEntry
import src.game.entity.selector.PhysicsSelector

class PhysicsSelectorRepository(using physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    override protected val dataById: Map[Int, PhysicsSelector] =
        def convertToPhysicsSelector(physicsSelectorEntries: Seq[PhysicsSelectorEntry]): PhysicsSelector =
            val physics = for {
                physicsSelectorEntry <- physicsSelectorEntries
                animation <- physicsRepository.findById(physicsSelectorEntry.physicsId)
            } yield {
                physicsSelectorEntry.state -> animation
            }

            PhysicsSelector(physics)

        FileReader.readFile(Resources.physicsSelectorEntriesFile, PhysicsSelectorEntry.reader)
            .groupBy(_.id)
            .view
            .mapValues(convertToPhysicsSelector)
            .toMap
