package net.ssanj.pusher.option1

object QMessageTypes {
  final case class PHandle(value: String)
  final case class QMessage(body: String, hash: String, handle: PHandle)
  final case class PMessage[A](entity: A, message: QMessage)
  final case class DeleteResult(value: String)
}