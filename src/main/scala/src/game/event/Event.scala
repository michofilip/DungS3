package src.game.event

import src.data.model.EntityEntry

import java.util.UUID

enum Event:

    case MoveTo(entityId: UUID, x: Int, y: Int) extends Event
    case MoveBy(entityId: UUID, dx: Int, dy: Int) extends Event
    case StartTimer extends Event
    case StopTimer extends Event
    case Despawn(entityIds: Seq[UUID]) extends Event
    case Spawn(useCurrentTimestamp: Boolean, entities: Seq[EntityEntry], events: Seq[Event]) extends Event
