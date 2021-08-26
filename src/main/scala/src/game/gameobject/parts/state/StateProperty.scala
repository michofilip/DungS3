package src.game.gameobject.parts.state

import src.game.gameobject.mapper.StateMapper
import src.game.gameobject.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

sealed abstract class StateProperty:
    val hasState: Boolean

    def state: Option[State]

    def stateTimestamp: Option[Timestamp]

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty

object StateProperty:

    final class EmptyStateProperty private[StateProperty]() extends StateProperty :
        override val hasState: Boolean = false

        override def state: Option[State] = None

        override def stateTimestamp: Option[Timestamp] = None

        override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty = this

    final class StatefullStateProperty private[StateProperty](_state: State, _stateTimestamp: Timestamp) extends StateProperty :
        override val hasState: Boolean = true

        override def state: Option[State] = None

        override def stateTimestamp: Option[Timestamp] = Some(_stateTimestamp)

        override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty =
            stateMapper(_state) match
                case state if state != _state => new StatefullStateProperty(state, timestamp)
                case _ => this


    val empty: StateProperty =
        new EmptyStateProperty()

    def apply(state: State, timestamp: Timestamp): StateProperty =
        new StatefullStateProperty(state, timestamp)
