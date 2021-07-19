package src.game.event2

import src.data.model.EntityEntry

import java.util.UUID

enum Event:

    case MoveTo(entityId: UUID, x: Int, y: Int) extends Event
    case MoveBy(entityId: UUID, dx: Int, dy: Int) extends Event
    case StartTimer extends Event
    case StopTimer extends Event
    case Kill(entityId: UUID) extends Event
    case Spawn(entity: EntityEntry) extends Event
