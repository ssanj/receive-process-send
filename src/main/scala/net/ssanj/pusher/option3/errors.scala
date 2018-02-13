package net.ssanj.pusher.option3

//App Errors
sealed trait PusherAppError
final case class QueueError(error: QError) extends PusherAppError
final case class MessageProcessingError(error: MessageError) extends PusherAppError
final case class NotificationSendError(error: NotificationClientError) extends PusherAppError

//Queue Errors
sealed trait QError
final case class ReceiveError(reason: String, error: Option[Throwable]) extends QError
final case object NoMessageError extends QError
final case class DeleteError(reason: String, error: Option[Throwable]) extends QError

//Message Error
sealed trait MessageError
final case class DecodeError(reason: String, error: Option[Throwable]) extends MessageError
final case class ValidationError(reason: String) extends MessageError

//NotificationSender Errors
sealed trait NotificationClientError
final case class EncodeError(reason: String, error: Option[Throwable]) extends NotificationClientError
final case class CallError(reason: String, error: Option[Throwable], call: ClientCall) extends NotificationClientError
