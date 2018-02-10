package net.ssanj.pusher.option1

import QMessageTypes._

object Model {
  final case class Config(url: String, apiKeys: Seq[String], whitelist: Seq[String])

  final case class InputMessage(name: String, age: Int)
  final case class OutputMessage(name: String, age: Int, date: Long)

  final case class PushResult(value: String)

  final case class ProcessResult(message: QMessage, deleteResult: DeleteResult, pushResults: Seq[PushResult])
}