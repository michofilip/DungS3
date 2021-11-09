package dod.utils

import java.io.File


object FileUtils:
    def filesInDir(dir: String): Seq[File] =
        val d = new File(dir)
        if (d.exists && d.isDirectory) then
            d.listFiles.filter(_.isFile).toSeq
        else
            Seq.empty
