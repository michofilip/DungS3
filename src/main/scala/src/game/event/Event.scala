package src.game.event

import src.data.model.GameObjectEntry

import java.util.UUID

enum Event:

    case MoveTo(gameObjectId: UUID, x: Int, y: Int) extends Event
    case MoveBy(gameObjectId: UUID, dx: Int, dy: Int) extends Event
    case StartTimer extends Event
    case StopTimer extends Event
    case Despawn(gameObjectIds: Seq[UUID]) extends Event
    case Spawn(useCurrentTimestamp: Boolean, gameObjects: Seq[GameObjectEntry], events: Seq[Event]) extends Event
