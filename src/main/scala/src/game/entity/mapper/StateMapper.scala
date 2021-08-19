package src.game.entity.mapper

import src.game.entity.parts.state.State

sealed abstract class StateMapper extends Mapper[State]

object StateMapper:

    case object SwitchOff extends StateMapper :
        override def apply(state: State): State = state match
            case State.On => State.Off
            case _ => state

    case object SwitchOn extends StateMapper :
        override def apply(state: State): State = state match
            case State.Off => State.On
            case _ => state

    case object Switch extends StateMapper :
        override def apply(state: State): State = state match
            case State.Off => State.On
            case State.On => State.Off
            case _ => state

    case object Open extends StateMapper :
        override def apply(state: State): State = state match
            case State.Closed => State.Open
            case _ => state

    case object Close extends StateMapper :
        override def apply(state: State): State = state match
            case State.Open => State.Closed
            case _ => state
