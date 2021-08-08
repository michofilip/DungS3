package src.game.entity.parts.state

import src.game.entity.mapper.StateMapper
import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

class StateProperty private(val state: Option[State], val stateTimestamp: Option[Timestamp]):

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty =
        val newState = stateMapper(state)

        if state != newState then
            new StateProperty(state = newState, stateTimestamp = Some(timestamp))
        else
            this

object StateProperty:

    lazy val empty: StateProperty =
        new StateProperty(state = None, stateTimestamp = None)

    def apply(state: State, timestamp: Timestamp): StateProperty =
        new StateProperty(state = Some(state), stateTimestamp = Some(timestamp))
