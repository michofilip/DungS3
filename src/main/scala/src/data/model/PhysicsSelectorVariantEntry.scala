package src.data.model

import src.game.entity.parts.State

import scala.util.Try
import scala.xml.Node

final case class PhysicsSelectorVariantEntry(state: Option[State], physicsId: Int)

object PhysicsSelectorVariantEntry:

    def fromXML(xml: Node): Option[PhysicsSelectorVariantEntry] = Try {
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val physicsId = (xml \ "physicsId").text.trim.toInt

        PhysicsSelectorVariantEntry(state, physicsId)
    }.toOption
