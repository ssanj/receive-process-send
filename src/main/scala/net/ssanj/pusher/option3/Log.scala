package net.ssanj.pusher.option3

trait Log {
    def info(message: String): Unit
    def error(message: String, error: Option[Throwable]): Unit
}