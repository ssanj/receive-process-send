package net.ssanj.pusher.option1

trait Log[A] {
    def logInfo(message: String): CouldNotLogErrorOr[Unit]
    def logError(message: String, error: Option[Throwable]): CouldNotLogErrorOr[Unit]
}