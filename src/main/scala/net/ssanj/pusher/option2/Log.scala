package net.ssanj.pusher.option2

trait Log {
    def info(message: String): Either[CouldNotLogError, Unit]
    def error(message: String, error: Option[Throwable]): Either[CouldNotLogError, Unit]
}