//package src.window
//
//import scalafx.scene.canvas.{Canvas, GraphicsContext}
//import scalafx.scene.image.Image
//import scalafx.scene.paint.Color
//import src.game.entity.Entity
//import src.game.entity.parts.position.Position
//
//class Screen(val tilesHorizontal: Int, val tilesVertical: Int, val tileWidth: Double, val tileHeight: Double) :
//    val canvas: Canvas = new Canvas(width = tilesHorizontal * tileWidth, height = tilesVertical * tileHeight)
//
//    def width: Double = canvas.width.toDouble
//
//    def height: Double = canvas.height.toDouble
//
//    private def graphicsContext2D: GraphicsContext = canvas.graphicsContext2D
//
//    def drawBackground(color: Color): Unit = {
//        graphicsContext2D.fill = color
//        graphicsContext2D.fillRect(0, 0, width, height)
//    }
//
//    def drawGrid(color: Color): Unit = {
//        graphicsContext2D.stroke = color
//        graphicsContext2D.lineWidth = 1
//
//        for (x <- 1 until tilesHorizontal) {
//            graphicsContext2D.strokeLine(x * tileWidth - .5, 0, x * tileWidth - .5, height)
//        }
//
//        for (y <- 1 until tilesVertical) {
//            graphicsContext2D.strokeLine(0, y * tileHeight - .5, width, y * tileHeight - .5)
//        }
//    }
//
//    def drawEntities(entities: Seq[Entity], focus: Position): Unit = {
//        case class Sprite(x: Double, y: Double, layer: Int, image: Image)
//
//        val horizontalOffset = focus.x - tilesHorizontal / 2
//        val verticalOffset = focus.y - tilesVertical / 2
//
//        def isOnScreen(position: Position): Boolean = {
//            val x = position.x - horizontalOffset
//            val y = position.y - verticalOffset
//
//            0 <= x && x < tilesHorizontal && 0 <= y && y < tilesVertical
//        }
//
//        def spriteFrom(position: Position, graphics: Graphics): Sprite = {
//            Sprite((position.x - horizontalOffset) * tileWidth, (position.y - verticalOffset) * tileHeight, graphics.layer, graphics.image)
//        }
//
//        entities.map { entity =>
//            (entity.position, entity.graphics)
//        }.collect {
//            case (Some(position), Some(graphics)) if isOnScreen(position) => spriteFrom(position, graphics)
//        }.sortBy { sprite =>
//            sprite.layer
//        }.foreach { sprite =>
//            graphicsContext2D.drawImage(sprite.image, sprite.x, sprite.y, tileWidth, tileHeight)
//        }
//    }
