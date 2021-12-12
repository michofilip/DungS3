package dod.game.gameobject.holder

import dod.game.gameobject.parts.commons.CommonsProperty
import dod.game.temporal.Timestamp

import java.util.UUID

trait CommonsHolder {
    protected val commonsProperty: CommonsProperty

    def name: String = commonsProperty.name

    def creationTimestamp: Timestamp = commonsProperty.creationTimestamp
}
