package src.data.repository

import src.data.Resources
import src.data.model.{AnimationEntry, FrameEntry}
import src.game.entity.parts.animation.{Animation, LoopingAnimation, SingleRunAnimation}

import scala.xml.XML

final class AnimationRepository(frameRepository: FrameRepository) extends Repository[Int, Animation] :

    override protected val dataById: Map[Int, Animation] =
        def convertToAnimation(animationEntry: AnimationEntry): Animation =
            val fps = animationEntry.fps
            val frames = animationEntry.frameIds.flatMap(frameRepository.findById).toIndexedSeq

            if animationEntry.looping then
                LoopingAnimation(fps = fps, frames = frames)
            else
                SingleRunAnimation(fps = fps, frames = frames)

        val xml = XML.load(Resources.animations.reader())

        (xml \ "Animation")
            .flatMap(AnimationEntry.fromXML)
            .map(animationEntry => animationEntry.id -> convertToAnimation(animationEntry))
            .toMap
