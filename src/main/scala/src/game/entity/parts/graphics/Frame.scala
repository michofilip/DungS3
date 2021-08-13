package src.game.entity.parts.graphics

import scalafx.scene.image.Image

final case class Frame(sprite: Image, layer: Int, offsetX: Float = 0, offsetY: Float = 0)
