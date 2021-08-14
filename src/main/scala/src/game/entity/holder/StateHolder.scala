package src.game.entity.holder

import src.game.entity.Entity
import src.game.entity.mapper.StateMapper
import src.game.entity.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

trait StateHolder[T <: Entity]:
    protected val stateProperty: StateProperty

    def hasState: Boolean = stateProperty.hasState

    def state: Option[State] = stateProperty.state

    def stateTimestamp: Option[Timestamp] = stateProperty.stateTimestamp

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): T 