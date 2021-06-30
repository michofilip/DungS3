package src.game.event

import src.game.GameState
import src.game.event.Event
import src.game.event.Event.EventResponse

import java.util.UUID

abstract class Event:
    def applyTo(gameState: GameState): EventResponse

object Event:

    type EventResponse = (GameState, Vector[Event])
    