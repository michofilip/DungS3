package src.game.entity.holder

import src.game.temporal.Timestamp

import java.util.UUID

trait CommonsHolder:
    val id: UUID
    val name: String
    val creationTimestamp: Timestamp
