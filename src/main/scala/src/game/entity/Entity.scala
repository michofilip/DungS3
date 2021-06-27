package src.game.entity

import src.game.entity.mapper.{DirectionMapper, PositionMapper, StateMapper}
import src.game.entity.parts.{Direction, Graphics, Physics, Position, State}
import src.game.entity.selector.{GraphicsSelector, PhysicsSelector}

import java.util.UUID

class Entity(val id: UUID,
             val name: String,
             val state: Option[State] = None,
             val position: Option[Position] = None,
             val direction: Option[Direction] = None,
             private val physicsSelector: PhysicsSelector = PhysicsSelector.empty,
             private val graphicsSelector: GraphicsSelector = GraphicsSelector.empty):

    def updated(state: StateMapper = StateMapper.Identity,
                position: PositionMapper = PositionMapper.Identity,
                direction: DirectionMapper = DirectionMapper.Identity): Entity =
        Entity(
            id = id,
            name = name,
            state = state(this.state),
            position = position(this.position),
            direction = direction(this.direction),
            physicsSelector = physicsSelector,
            graphicsSelector = graphicsSelector
        )

    def physics: Option[Physics] =
        physicsSelector.selectPhysics(state)

    def graphics: Option[Graphics] =
        graphicsSelector.selectGraphics(state, direction)

    def hasState: Boolean = state.isDefined

    def hasPosition: Boolean = position.isDefined

    def hasDirection: Boolean = direction.isDefined

    override def toString: String =
        Seq(
            Some(s"id=$id"),
            state.map(state => s"state=$state"),
            position.map(position => s"position=$position"),
            direction.map(direction => s"direction=$direction")
        ).flatten.mkString("Entity(", ", ", ")")
