package src.data.file

import java.io.{BufferedWriter, File, PrintWriter}

object FileWriter:

    trait Writer[T] extends (T => Array[String])

    def writeFile[T](file: File, entries: Seq[T], writer: Writer[T]): Unit =
        val printWriter = PrintWriter(BufferedWriter(java.io.FileWriter(file)))

        entries
            .map(writer)
            .map(_.mkString("; "))
            .foreach(printWriter.println)

        printWriter.close()
    