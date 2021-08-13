package src.game.entity.parts.state

import src.game.entity.mapper.StateMapper
import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

sealed abstract class StateProperty:
    val hasState: Boolean

    def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty

object StateProperty:

    case object EmptyStateProperty extends StateProperty :
        override val hasState: Boolean = false

        override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty = this

    final case class StatefullStateProperty(state: State, stateTimestamp: Timestamp) extends StateProperty :
        override val hasState: Boolean = true

        override def updatedState(stateMapper: StateMapper, timestamp: Timestamp): StateProperty =
            stateMapper(state) match
                case state if state != this.state => copy(state = state, stateTimestamp = timestamp)
                case _ => this
    

    val empty: StateProperty =
        EmptyStateProperty

    def apply(state: State, timestamp: Timestamp): StateProperty =
        StatefullStateProperty(state = state, stateTimestamp = timestamp)
