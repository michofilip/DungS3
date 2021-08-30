package dod.game.gameobject.holder

import dod.game.temporal.Timestamp

import java.util.UUID

trait CommonsHolder:
    val id: UUID
    val name: String
    val creationTimestamp: Timestamp
