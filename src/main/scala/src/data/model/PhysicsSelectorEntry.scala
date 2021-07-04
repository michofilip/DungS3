package src.data.model

import src.data.file.FileReader.{Reader, *}
import src.game.entity.parts.State

import scala.util.Try
import scala.xml.Node

case class PhysicsSelectorEntry(id: Int, state: Option[State], physicsId: Int)

object PhysicsSelectorEntry:

    val reader: Reader[PhysicsSelectorEntry] = strArr => Try {
        val id = strArr(0).asInt
        val state = strArr(1).asOption(State.valueOf)
        val physicsId = strArr(2).asInt

        PhysicsSelectorEntry(id = id, state = state, physicsId = physicsId)
    }.toOption

    def fromXML(xml: Node): Option[PhysicsSelectorEntry] = Try {
        val id = (xml \ "id").text.trim.toInt
        val state = (xml \ "state").map(_.text.trim).map(State.valueOf).headOption
        val physicsId = (xml \ "physics-id").text.trim.toInt

        PhysicsSelectorEntry(id, state, physicsId)
    }.toOption
