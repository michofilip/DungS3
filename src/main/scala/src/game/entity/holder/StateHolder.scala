package src.game.entity.holder

import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

trait StateHolder:
    protected val stateProperty: StateProperty

    def state: Option[State] = stateProperty.state

    def stateTimestamp: Option[Timestamp] = stateProperty.stateTimestamp

    def hasState: Boolean = state.isDefined
