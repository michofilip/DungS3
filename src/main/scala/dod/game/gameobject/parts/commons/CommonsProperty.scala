package dod.game.gameobject.parts.commons

import dod.game.gameobject.parts.graphics.{AnimationSelector, GraphicsProperty}
import dod.game.gameobject.parts.position.Direction
import dod.game.gameobject.parts.state.State
import dod.game.temporal.{Duration, Timestamp}

final class CommonsProperty(val name: String,
                            val creationTimestamp: Timestamp) 
