package src.game.entity.parts.graphics

import src.game.entity.parts.graphics.GraphicsProperty

class GraphicsProperty private(val animationSelector: Option[AnimationSelector])

object GraphicsProperty:

    lazy val empty: GraphicsProperty =
        new GraphicsProperty(animationSelector = None)

    def apply(animationSelector: AnimationSelector): GraphicsProperty =
        new GraphicsProperty(animationSelector = Some(animationSelector))
