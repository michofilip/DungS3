package src.data.repository

import src.data.file.{FileReader, Resources}
import src.data.model.{AnimationEntry, FrameEntry}
import src.game.entity.parts.animation.{Animation, LoopingAnimation, SingleRunAnimation}

import scala.xml.XML

class AnimationRepository(using frameRepository: FrameRepository) extends Repository[Int, Animation] :

    override protected val dataById: Map[Int, Animation] =
        def convertToAnimation(animationEntriy: AnimationEntry): Animation =
            val fps = animationEntriy.fps
            val frames = animationEntriy.frameIds.flatMap(frameRepository.findById).toIndexedSeq

            if animationEntriy.looping then
                LoopingAnimation(fps = fps, frames = frames)
            else
                SingleRunAnimation(fps = fps, frames = frames)

        val xml = XML.loadFile(Resources.animationsFile)

        (xml \ "Animation")
            .flatMap(AnimationEntry.fromXML)
            .map(animationEntry => animationEntry.id -> convertToAnimation(animationEntry))
            .toMap
