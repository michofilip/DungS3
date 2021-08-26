package src.data.repository

import src.data.Resources
import src.data.model.AnimationEntity
import src.game.gameobject.parts.graphics.Animation
import src.utils.TryUtils.*

import scala.util.{Failure, Success, Try}
import scala.xml.XML

final class AnimationRepository private(frameRepository: FrameRepository) extends Repository[Int, Animation] :

    override protected val dataById: Map[Int, Animation] =
        def animationFrom(animationEntity: AnimationEntity): Try[Animation] =
            val frames = animationEntity.frameIds.map { frameId =>
                frameRepository.findById(frameId).toTry {
                    new NoSuchElementException(s"Frame id: $frameId not found!")
                }
            }.toTrySeq

            for
                frames <- frames
            yield
                Animation(
                    fps = animationEntity.fps,
                    frames = frames,
                    looping = animationEntity.looping
                )

        val xml = XML.load(Resources.animations.reader())

        (xml \ "Animation").map { node =>
            for
                animationEntity <- AnimationEntity.fromXML(node)
                animation <- animationFrom(animationEntity)
            yield
                animationEntity.id -> animation
        }.toTrySeq.map(_.toMap).get

object AnimationRepository:

    private lazy val animationRepository = new AnimationRepository(FrameRepository())

    def apply(): AnimationRepository = animationRepository
    