package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.mapper.StateMapper
import dod.game.gameobject.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import dod.game.gameobject.parts.state.{State, StateProperty}
import dod.game.temporal.Timestamp

trait StateHolder[T <: GameObject]:
    protected val stateProperty: StateProperty

    def hasState: Boolean = stateProperty.hasState

    def state: Option[State] = stateProperty.state

    def stateTimestamp: Option[Timestamp] = stateProperty.stateTimestamp

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): T 