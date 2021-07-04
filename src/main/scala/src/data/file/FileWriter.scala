package src.data.file

import java.io.{BufferedWriter, File, PrintWriter}

@Deprecated
object FileWriter:

    trait Writer[T] extends (T => Array[String])

    @Deprecated
    def writeFile[T](file: File, entries: Seq[T], writer: Writer[T]): Unit =
        val printWriter = PrintWriter(BufferedWriter(java.io.FileWriter(file)))

        entries
            .map(writer)
            .map(_.mkString("; "))
            .foreach(printWriter.println)

        printWriter.close()
    