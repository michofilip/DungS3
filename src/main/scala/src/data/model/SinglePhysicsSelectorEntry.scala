package src.data.model

import src.game.entity.parts.State

import scala.util.Try
import scala.xml.Node

case class SinglePhysicsSelectorEntry(state: Option[State], physicsId: Int)

object SinglePhysicsSelectorEntry:

    def fromXML(xml: Node): Option[SinglePhysicsSelectorEntry] = Try {
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val physicsId = (xml \ "physics-id").text.trim.toInt

        SinglePhysicsSelectorEntry(state, physicsId)
    }.toOption
