package dod.data.repository

import dod.data.Resources
import dod.data.model.{PhysicsEntity, PhysicsSelectorEntity}
import dod.game.gameobject.parts.physics.PhysicsSelector
import dod.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsSelectorRepository private(physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    protected val dataById: Map[Int, PhysicsSelector] =
        def physicsSelectorFrom(physicsSelectorEntity: PhysicsSelectorEntity): Try[PhysicsSelector] =
            val physics = physicsSelectorEntity.variants.map { variant =>
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
                physicsSelectorEntity <- PhysicsSelectorEntity.fromXML(node)
                physicsSelector <- physicsSelectorFrom(physicsSelectorEntity)
            yield
                physicsSelectorEntity.id -> physicsSelector
        }.toTrySeq.map(_.toMap).get

object PhysicsSelectorRepository:

    private lazy val physicsSelectorRepository = new PhysicsSelectorRepository(PhysicsRepository())

    def apply(): PhysicsSelectorRepository = physicsSelectorRepository