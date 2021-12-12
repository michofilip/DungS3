package dod.game.gameobject.parts.state

import dod.game.gameobject.mapper.StateMapper
import dod.game.gameobject.parts.state.{State, StateProperty}
import dod.game.temporal.Timestamp

final class StateProperty(val state: State,
                          val stateTimestamp: Timestamp) {

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty =
        stateMapper(state) match
            case state if state != this.state => new StateProperty(state, timestamp)
            case _ => this
}
