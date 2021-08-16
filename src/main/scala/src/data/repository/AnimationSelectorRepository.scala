package src.data.repository

import src.data.Resources
import src.data.model.AnimationSelectorEntry
import src.game.entity.parts.graphics.AnimationSelector
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class AnimationSelectorRepository private(animationRepository: AnimationRepository) extends Repository[Int, AnimationSelector] :

    override protected val dataById: Map[Int, AnimationSelector] =
        def animationSelectorFrom(animationSelectorEntry: AnimationSelectorEntry): Try[AnimationSelector] =
            animationSelectorEntry.variants.map { variant =>
                animationRepository.findById(variant.animationId).map { animation =>
                    Success {
                        (variant.state, variant.direction) -> animation
                    }
                }.getOrElse {
                    Failure {
                        new NoSuchElementException(s"Animation id: ${variant.animationId} not found!")
                    }
                }
            }.invertTry.map { animations =>
                AnimationSelector(animations)
            }

        val xml = XML.load(Resources.animationSelectors.reader())

        (xml \ "PhysicsSelector").map { node =>
            AnimationSelectorEntry.fromXML(node).flatMap { animationSelectorEntry =>
                animationSelectorFrom(animationSelectorEntry).map { animationSelector =>
                    animationSelectorEntry.id -> animationSelector
                }
            }
        }.invertTry.map(_.toMap).get

object AnimationSelectorRepository:

    private lazy val animationSelectorRepository = new AnimationSelectorRepository(AnimationRepository())

    def apply(): AnimationSelectorRepository = animationSelectorRepository
