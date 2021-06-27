package src.game.entity.selector

import src.game.entity.parts.{Direction, Graphics, State}

final class GraphicsSelector private(graphicsMap: Map[(Option[State], Option[Direction]), Graphics]):
    def selectGraphics(state: Option[State], direction: Option[Direction]): Option[Graphics] = graphicsMap.get(state, direction)

object GraphicsSelector:
    def apply(graphics: ((Option[State], Option[Direction]), Graphics)*): GraphicsSelector =
        new GraphicsSelector(graphics.toMap)

    val empty: GraphicsSelector = GraphicsSelector()

