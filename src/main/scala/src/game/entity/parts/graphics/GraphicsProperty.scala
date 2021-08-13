package src.game.entity.parts.graphics

import src.game.entity.parts.graphics.GraphicsProperty

class GraphicsProperty private(val layer: Option[Int], val animationSelector: Option[AnimationSelector])

object GraphicsProperty:

    lazy val empty: GraphicsProperty =
        new GraphicsProperty(layer = None, animationSelector = None)

    def apply(layer: Int, animationSelector: AnimationSelector): GraphicsProperty =
        new GraphicsProperty(layer = Some(layer), animationSelector = Some(animationSelector))
