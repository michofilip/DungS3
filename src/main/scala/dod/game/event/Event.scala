package dod.game.event

import dod.data.model.GameObjectEntity

import java.util.UUID

enum Event:

    case MoveTo(gameObjectId: UUID, x: Int, y: Int) extends Event
    case MoveBy(gameObjectId: UUID, dx: Int, dy: Int) extends Event
    case StartTimer extends Event
    case StopTimer extends Event
    case Despawn(gameObjectIds: Seq[UUID]) extends Event
    case Spawn(useCurrentTimestamp: Boolean, gameObjects: Seq[GameObjectEntity], events: Seq[Event]) extends Event
