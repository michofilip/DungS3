package src.utils

object StringUtils:

    extension (string: String)
        def removeFileExtension: String =
            string.substring(0, string.lastIndexOf('.'))

