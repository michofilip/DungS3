package src.game.entity

import src.data.repository.EntityPrototypeRepository
import src.game.entity.mapper.{DirectionMapper, PositionMapper, StateMapper}
import src.game.entity.parts.animation.Animation
import src.game.entity.parts.{Direction, Physics, Position, State}
import src.game.entity.selector.{AnimationSelector, PhysicsSelector}
import src.game.temporal.Timestamp

import java.util.UUID

class Entity private[entity](val id: UUID,
                             val name: String,
                             val state: Option[State],
                             val stateTimestamp: Option[Timestamp],
                             val position: Option[Position],
                             val direction: Option[Direction],
                             private val physicsSelector: PhysicsSelector,
                             private val animationSelector: AnimationSelector):

    def updated(state: StateMapper = StateMapper.Identity,
                position: PositionMapper = PositionMapper.Identity,
                direction: DirectionMapper = DirectionMapper.Identity,
                timestamp: Timestamp): Entity =
        val newState = state(this.state)
        val newStateChangeTimestamp = if (this.state != newState) Some(timestamp) else stateTimestamp

        Entity(
            id = id,
            name = name,
            state = newState,
            stateTimestamp = stateTimestamp,
            position = position(this.position),
            direction = direction(this.direction),
            physicsSelector = physicsSelector,
            animationSelector = animationSelector
        )

    def physics: Option[Physics] =
        physicsSelector.selectPhysics(state)

    def animation: Option[Animation] =
        animationSelector.selectAnimation(state, direction)

    def hasState: Boolean =
        state.isDefined

    def hasPosition: Boolean =
        position.isDefined

    def hasDirection: Boolean =
        direction.isDefined

    override def toString: String =
        Seq(
            Some(s"id=$id"),
            Some(s"name=$name"),
            state.map(state => s"state=$state"),
            stateTimestamp.map(stateTimestamp => s"stateTimestamp=$stateTimestamp"),
            position.map(position => s"position=$position"),
            direction.map(direction => s"direction=$direction")
        ).flatten.mkString("Entity(", ", ", ")")
