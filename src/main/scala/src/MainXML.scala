package src

import scala.util.Try
import scala.xml.{Elem, Node, PrettyPrinter, XML}

object MainXML:

    @main
    def run(): Unit =

        val prettyPrinter = new PrettyPrinter(80, 2)

        case class TestFrame(id: Int):
            def xml =
                <TestFrame>
                    <id>
                        {id}
                    </id>
                </TestFrame>

        case class TestAnimation(fps: Double, frames: IndexedSeq[TestFrame]):
            def xml =
                <TestAnimation>
                    <fps>
                        {fps}
                    </fps>
                    <frames>
                        {frames.map(_.xml)}
                    </frames>
                </TestAnimation>

        val testFrame = TestFrame(id = 1)

        val testAnimation = TestAnimation(
            fps = 30.0,
            frames = IndexedSeq(
                TestFrame(id = 1),
                TestFrame(id = 2),
                TestFrame(id = 3),
                TestFrame(id = 4)
            )
        )

        println(testFrame)
        println()
        println(prettyPrinter.format(testFrame.xml))
        println()
        println(testAnimation)
        println()
        println(prettyPrinter.format(testAnimation.xml))

        def xmlToTestFrame(xml: Node): Option[TestFrame] = Try {
            val id = (xml \ "id").text.trim.toInt
            TestFrame(id = id)
        }.toOption

        val testFrameXml = testFrame.xml
        println(xmlToTestFrame(testFrameXml))

        def xmlToTestAnimation(xml: Node): Option[TestAnimation] = Try {
            val fps = (xml \ "fps").text.trim.toDouble
            val frames = (xml \ "frames" \ "TestFrame").flatMap(xmlToTestFrame).toIndexedSeq

            TestAnimation(fps = fps, frames = frames)
        }.toOption

        val testAnimationXml = testAnimation.xml
        println(xmlToTestAnimation(testAnimationXml))

