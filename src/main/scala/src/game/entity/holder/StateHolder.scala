package src.game.entity.holder

import src.game.entity.parts.state.StateProperty.{EmptyStateProperty, StatefullStateProperty}
import src.game.entity.parts.state.{State, StateProperty}
import src.game.temporal.Timestamp

trait StateHolder:
    protected val stateProperty: StateProperty

    def hasState: Boolean = stateProperty.hasState

    def state: Option[State] = stateProperty match {
        case EmptyStateProperty => None
        case StatefullStateProperty(state, _) => Some(state)
    }

    def stateTimestamp: Option[Timestamp] = stateProperty match {
        case EmptyStateProperty => None
        case StatefullStateProperty(_, stateTimestamp) => Some(stateTimestamp)
    }
