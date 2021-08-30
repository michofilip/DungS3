package dod

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.stage.StageStyle
import dod.data.repository.TileSetRepository

object Game extends JFXApp3 {

    override def start(): Unit = {
        val tileRepository: TileSetRepository = TileSetRepository()
        tileRepository.findAll.foreach(println)



        val primaryStage = new PrimaryStage {
            //            initStyle(StageStyle.Undecorated)
            title = "Game"
            resizable = false
            scene = new Scene {
                onKeyPressed = { keyEvent =>
                    //                    gameActor ! GameActor.ProcessKeyEvent(keyEvent)
                }

                root = new Pane {
                    //                    children = screen.canvas
                }
            }
            //            onCloseRequest = { _ =>
            //                println("Closing window")
            //                gameActor ! GameActor.Shutdown
            //                Platform.exit()
            //            }
        }

        stage = primaryStage
    }
}
