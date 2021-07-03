package src.data.model

import src.data.file.FileReader.{Reader, *}

import scala.util.Try

case class PhysicsEntry(id: Int, solid: Boolean, opaque: Boolean)

object PhysicsEntry:

    val reader: Reader[PhysicsEntry] = strArr => Try {
        val id = strArr(0).asInt
        val solid = strArr(1).asBoolean
        val opaque = strArr(2).asBoolean

        PhysicsEntry(id = id, solid = solid, opaque = opaque)
    }.toOption
