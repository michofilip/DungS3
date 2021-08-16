package src.data.repository

import src.data.Resources
import src.data.model.{PhysicsEntry, PhysicsSelectorEntry}
import src.game.entity.parts.physics.PhysicsSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.{NodeSeq, XML}

final class PhysicsSelectorRepository private(physicsRepository: PhysicsRepository) extends Repository[Int, PhysicsSelector] :

    protected val dataById: Map[Int, PhysicsSelector] =
        def physicsSelectorFrom(physicsSelectorEntry: PhysicsSelectorEntry): Try[PhysicsSelector] =
            physicsSelectorEntry.variants.map { variant =>
                physicsRepository.findById(variant.physicsId).map { physics =>
                    Success {
                        variant.state -> physics
                    }
                }.getOrElse {
                    Failure {
                        new NoSuchElementException(s"Physics id: ${variant.physicsId} not found!")
                    }
                }
            }.invertTry.map { physics =>
                PhysicsSelector(physics)
            }

        val xml = XML.load(Resources.physicsSelectors.reader())

        (xml \ "PhysicsSelector").map { node =>
            PhysicsSelectorEntry.fromXML(node).flatMap { physicsSelectorEntry =>
                physicsSelectorFrom(physicsSelectorEntry).map { physicsSelector =>
                    physicsSelectorEntry.id -> physicsSelector
                }
            }
        }.invertTry.map(_.toMap).get

object PhysicsSelectorRepository:

    private lazy val physicsSelectorRepository = new PhysicsSelectorRepository(PhysicsRepository())

    def apply(): PhysicsSelectorRepository = physicsSelectorRepository