package src.data.file

import java.io.File
import scala.io.Source
import scala.reflect.ClassTag

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

    extension (str: String)
        def asInt: Int =
            str.toInt

        def asLong: Long =
            str.toLong

        def asFloat: Float =
            str.toFloat

        def asDouble: Double =
            str.toDouble

        def asBoolean: Boolean =
            !(str == "0")

        def asOption[T](mapping: String => T): Option[T] =
            if str.nonEmpty then Some(mapping(str)) else None

        def asSeq[T](mapping: String => T)(using ClassTag[T]): Seq[T] =
            str.split(',').map(_.trim).filter(_.nonEmpty).map(mapping).toSeq
