package src.game.entity.mapper

import src.game.entity.parts.state.State

sealed abstract class StateMapper extends Mapper[State]

object StateMapper:

    @Deprecated
    case object Identity extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt

    @Deprecated
    final case class SetState(state: State) extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = Some(state)

    @Deprecated
    case object RemoveState extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = None

    case object SwitchOff extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt match
            case Some(State.On) => Some(State.Off)
            case _ => stateOpt

    case object SwitchOn extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt match
            case Some(State.Off) => Some(State.On)
            case _ => stateOpt

    case object Switch extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt match
            case Some(State.Off) => Some(State.On)
            case Some(State.On) => Some(State.Off)
            case _ => stateOpt

    case object Open extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt match
            case Some(State.Closed) => Some(State.Open)
            case _ => stateOpt

    case object Close extends StateMapper :
        override def apply(stateOpt: Option[State]): Option[State] = stateOpt match
            case Some(State.Open) => Some(State.Closed)
            case _ => stateOpt
