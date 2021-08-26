package src.game.gameobject.holder

import src.game.gameobject.GameObject
import src.game.gameobject.mapper.StateMapper
import src.game.gameobject.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import src.game.gameobject.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

trait StateHolder[T <: GameObject]:
    protected val stateProperty: StateProperty

    def hasState: Boolean = stateProperty.hasState

    def state: Option[State] = stateProperty.state

    def stateTimestamp: Option[Timestamp] = stateProperty.stateTimestamp

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): T 