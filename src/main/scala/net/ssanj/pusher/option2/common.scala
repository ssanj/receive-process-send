package net.ssanj.pusher.option2

trait EitherOps[L, R] {
  def leftMap[L2](f: L => L2): Either[L2, R]
}

trait Json

trait Decode[A] {
  def decode(value: String): Either[DecodeError, A]
}

trait Encode[A] {
  def encode(value: A): Either[EncodeError, Json]
}

final case class Config(url: String, apiKeys: Seq[String], whitelist: Seq[String])
final case class InputMessage(name: String, age: Int)
final case class OutputMessage(name: String, age: Int, date: Long)
final case class NotificationResult(value: String)
final case class QueueReadResult(message: QMessage, deleteResult: DeleteResult)
final case class ProcessResult(qReadResult: QueueReadResult, notificationResults: Seq[NotificationResult])

final case class QHandle(value: String)
final case class QMessage(body: String, hash: String, handle: QHandle)
final case class PMessage[A](entity: A, message: QMessage)
final case class DeleteResult(value: String)

//App Errors
sealed trait PusherAppError
final case class PusherQueueReaderError(error: QueueReaderError) extends PusherAppError
final case class PusherMessageProcessingError(error: MessageProcessingError) extends PusherAppError
final case class PusherNotificationSendError(error: NotificationSenderError) extends PusherAppError

//QueueReader Errors
sealed trait QueueReaderError
final case class ReaderError(error: QError) extends QueueReaderError
final case class ReaderLogError(error: CouldNotLogError) extends QueueReaderError

//Queue Errors
sealed trait QError
final case class QReceiveError(reason: String, error: Option[Throwable]) extends QError
final case object QNoMessageError extends QError
final case class QMessageDeleteError(reason: String, error: Option[Throwable]) extends QError

//MessageProcessor Error
sealed trait MessageProcessingError
final case class MessageProcessingDecodeError(error: DecodeError) extends MessageProcessingError
final case class MessageProcessingEncodeError(error: EncodeError) extends MessageProcessingError
final case class MessageProcessingValidationError(error: MessageValidationError) extends MessageProcessingError

//NotificationSender Errors
sealed trait NotificationSenderError
final case class NotificationSenderEncodeError(error: EncodeError) extends NotificationSenderError
final case class NotificationSenderClientError(error: NotificationClientError) extends NotificationSenderError

//Base Errors
final case class MessageValidationError(reason: String)
final case class EncodeError(reason: String, error: Option[Throwable])
final case class NotificationClientError(reason: String, error: Option[Throwable])
final case class DecodeError(reason: String, error: Option[Throwable])
final case class CouldNotLogError(reason: String, error: Option[Throwable])