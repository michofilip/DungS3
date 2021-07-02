package src.data.model

import src.data.file.FileReader.Reader
import src.game.entity.parts.State

import scala.util.Try

case class PhysicsSelectorEntry(name: String, state: Option[State], physicsId: Int)

object PhysicsSelectorEntry:

    val reader: Reader[PhysicsSelectorEntry] = strArr => Try {
        val name = strArr(0)
        val state = if strArr(1).nonEmpty then Some(State.valueOf(strArr(1))) else None
        val physicsId = strArr(2).toInt

        PhysicsSelectorEntry(name = name, state = state, physicsId = physicsId)
    }.toOption
