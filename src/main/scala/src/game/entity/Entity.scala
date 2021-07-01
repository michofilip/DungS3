package src.game.entity

import src.game.entity.mapper.{DirectionMapper, PositionMapper, StateMapper}
import src.game.entity.parts.animation.Animation
import src.game.entity.parts.{Direction, Graphics, Physics, Position, State}
import src.game.entity.selector.{AnimationSelector, GraphicsSelector, PhysicsSelector}
import src.game.temporal.Timestamp

import java.util.UUID

class Entity(val id: UUID,
             val name: String,
             val state: Option[State] = None,
             val stateTimestamp: Option[Timestamp] = None,
             val position: Option[Position] = None,
             val direction: Option[Direction] = None,
             private val physicsSelector: PhysicsSelector = PhysicsSelector.empty,
             @Deprecated private val graphicsSelector: GraphicsSelector = GraphicsSelector.empty,
             private val animationSelector: AnimationSelector = AnimationSelector.empty):

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
            graphicsSelector = graphicsSelector,
            animationSelector = animationSelector
        )

    def physics: Option[Physics] =
        physicsSelector.selectPhysics(state)

    @Deprecated
    def graphics: Option[Graphics] =
        graphicsSelector.selectGraphics(state, direction)

    def animation: Option[Animation] =
        animationSelector.selectAnimation(state, direction)

    def hasState: Boolean = state.isDefined

    def hasPosition: Boolean = position.isDefined

    def hasDirection: Boolean = direction.isDefined

    override def toString: String =
        Seq(
            Some(s"id=$id"),
            Some(s"name=$name"),
            state.map(state => s"state=$state"),
            stateTimestamp.map(stateChangeTimestamp => s"stateChangeTimestamp=$stateChangeTimestamp"),
            position.map(position => s"position=$position"),
            direction.map(direction => s"direction=$direction")
        ).flatten.mkString("Entity(", ", ", ")")
