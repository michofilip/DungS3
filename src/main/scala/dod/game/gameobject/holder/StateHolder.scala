package dod.game.gameobject.holder

import dod.game.gameobject.GameObject
import dod.game.gameobject.mapper.StateMapper
import dod.game.gameobject.parts.state.{State, StateProperty}
import dod.game.temporal.Timestamp

trait StateHolder[T <: GameObject] {
    protected val stateProperty: Option[StateProperty]

    def hasState: Boolean = stateProperty.isDefined

    def state: Option[State] = stateProperty.map(_.state)

    def stateTimestamp: Option[Timestamp] = stateProperty.map(_.stateTimestamp)

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): T
}
