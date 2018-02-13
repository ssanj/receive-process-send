package net.ssanj.pusher.option3

//config
final case class Config(url: String, apiKeys: Seq[String], whitelist: Seq[String])

//Message types
final case class InputMessage(name: String, id: Int)
final case class OutputMessage(name: String, id: Int, date: Long)

//Sender-related
final case class ClientCall(endpoint: String, payload: String, statusCode: Int, statusText: String)
final case class NotificationResult(value: String)
final case class ProcessResult(qReadResult: QueueReadResult, notificationResults: Seq[NotificationResult])

//Queue-related
final case class QueueReadResult(message: QMessage, deleteResult: DeleteResult)
final case class QHandle(value: String)
final case class QMessage(body: String, hash: String, handle: QHandle)
final case class DeleteResult(value: String)

