package src.data.repository

import src.data.Resources
import src.data.model.{PhysicsEntry, PhysicsSelectorEntry}
import src.game.gameobject.parts.physics.PhysicsSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsSelectorRepository private(physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    protected val dataById: Map[Int, PhysicsSelector] =
        def physicsSelectorFrom(physicsSelectorEntry: PhysicsSelectorEntry): Try[PhysicsSelector] =
            val physics = physicsSelectorEntry.variants.map { variant =>
                physicsRepository.findById(variant.physicsId).map { physics =>
                    variant.state -> physics
                }.toTry {
                    new NoSuchElementException(s"Physics id: ${variant.physicsId} not found!")
                }
            }.toTrySeq

            for
                physics <- physics
            yield
                PhysicsSelector(physics)

        val xml = XML.load(Resources.physicsSelectors.reader())

        (xml \ "PhysicsSelector").map { node =>
            for
                physicsSelectorEntry <- PhysicsSelectorEntry.fromXML(node)
                physicsSelector <- physicsSelectorFrom(physicsSelectorEntry)
            yield
                physicsSelectorEntry.id -> physicsSelector
        }.toTrySeq.map(_.toMap).get

object PhysicsSelectorRepository:

    private lazy val physicsSelectorRepository = new PhysicsSelectorRepository(PhysicsRepository())

    def apply(): PhysicsSelectorRepository = physicsSelectorRepository