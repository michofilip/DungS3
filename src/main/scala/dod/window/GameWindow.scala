package dod.window

import dod.game.GameState
import dod.game.gameobject.GameObject
import dod.game.gameobject.parts.graphics.Frame
import dod.game.gameobject.parts.position.Position
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

class GameWindow(val tilesHorizontal: Int, val tilesVertical: Int, val tileWidth: Double, val tileHeight: Double) {
    val canvas: Canvas = new Canvas(width = tilesHorizontal * tileWidth, height = tilesVertical * tileHeight)
    val width: Double = canvas.width.toDouble
    val height: Double = canvas.height.toDouble

    private def graphicsContext2D: GraphicsContext = canvas.graphicsContext2D

    def drawBackground(color: Color): Unit =
        graphicsContext2D.fill = color
        graphicsContext2D.fillRect(0, 0, width, height)


    def drawGrid(color: Color): Unit =
        graphicsContext2D.stroke = color
        graphicsContext2D.lineWidth = 1

        for (x <- 1 until tilesHorizontal)
            graphicsContext2D.strokeLine(x * tileWidth - .5, 0, x * tileWidth - .5, height)

        for (y <- 1 until tilesVertical)
            graphicsContext2D.strokeLine(0, y * tileHeight - .5, width, y * tileHeight - .5)

    def drawGameState(gameState: GameState): Unit = {
        case class Sprite(x: Double, y: Double, layer: Int, image: Image):
            def isOnScreen: Boolean =
                x + image.width.toDouble > 0 && x < width && y + image.height.toDouble > 0 && y < height

        val focus = Position(0, 0)

        val timestamp = gameState.timer.timestamp
        val horizontalOffset = focus.x - tilesHorizontal / 2.0
        val verticalOffset = focus.y - tilesVertical / 2.0

        def spriteFrom(gameObject: GameObject): Option[Sprite] =
            for
                position <- gameObject.position
                frame <- gameObject.frame(timestamp)
                layer <- gameObject.layer
            yield
                Sprite(
                    x = (position.x - horizontalOffset + frame.offsetX) * tileWidth,
                    y = (position.y - verticalOffset + frame.offsetY) * tileHeight,
                    layer = layer,
                    image = frame.sprite
                )

        gameState.gameObjects.findAll
            .flatMap(spriteFrom)
            .filter(_.isOnScreen)
            .sortBy(_.layer)
            .foreach { sprite =>
                graphicsContext2D.drawImage(sprite.image, sprite.x, sprite.y, tileWidth, tileHeight)
            }
    }
}
