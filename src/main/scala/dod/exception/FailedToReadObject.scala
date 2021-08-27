package dod.exception

class FailedToReadObject(subject: String, reason: String)
    extends RuntimeException(s"Failed to create $subject, reason: $reason")
