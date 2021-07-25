package src

import scala.annotation.tailrec
import scala.collection.immutable.Queue
import scala.util.Random

object MainTest:

    @main
    def runTest(): Unit =
        testQueue()
        testVector()
        println(testVector())
        println(testQueue())

    def testVector(): Long =
        val vector = Vector.fill(1000)(1)

        val random = new Random(0)

        @tailrec
        def f(vector: Vector[Int], iter: Int): Unit =
            if iter > 0 then
                if random.nextBoolean() || vector.isEmpty then
                    f(vector :+ 1, iter - 1)
                else
                    f(vector.tail, iter - 1)
            else ()

        val time = System.nanoTime()
        f(vector, 1000000)
        System.nanoTime() - time


    def testQueue(): Long =
        val queue = Queue.fill(1000)(1)

        val random = new Random(0)

        @tailrec
        def f(queue: Queue[Int], iter: Int): Unit =
            if iter > 0 then
                if random.nextBoolean() || queue.isEmpty then
                    f(queue :+ 1, iter - 1)
                else
                    f(queue.tail, iter - 1)
            else ()

        val time = System.nanoTime()
        f(queue, 1000000)
        System.nanoTime() - time