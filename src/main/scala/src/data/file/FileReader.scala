package src.data.file

import java.io.File
import scala.io.Source

object FileReader:

    trait Reader[T] extends (Array[String] => Option[T])

    def readFile[T](file: File, reader: Reader[T]): Seq[T] =
        val bufferedSource = Source.fromFile(file)

        val entries: Seq[T] = bufferedSource.getLines().toSeq.view
            .map(_.trim)
            .filterNot(_.startsWith("#"))
            .filterNot(_.isEmpty)
            .map(_.split(';').map(_.trim))
            .flatMap(reader)
            .toSeq

        bufferedSource.close()

        entries
