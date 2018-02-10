package net.ssanj.pusher.option1

trait EitherOps[L, R] {
  def leftMap[L2](f: L => L2): Either[L2, R]
}

trait Json

trait Decode[A]

trait Encode[A]

final case class Config(url: String, apiKeys: Seq[String], whitelist: Seq[String])
final case class InputMessage(name: String, age: Int)
final case class OutputMessage(name: String, age: Int, date: Long)
final case class PushResult(value: String)
final case class ProcessResult(message: QMessage, deleteResult: DeleteResult, pushResults: Seq[PushResult])

final case class PHandle(value: String)
final case class QMessage(body: String, hash: String, handle: PHandle)
final case class PMessage[A](entity: A, message: QMessage)
final case class DeleteResult(value: String)

sealed trait PusherAppErrors
final case class PusherMessageReceiveError(error: MessageReceiveError) extends PusherAppErrors
final case class PusherDeleteMessageError(error: DeleteMessageError) extends PusherAppErrors
final case class PusherCouldNotLogError(error: CouldNotLogError) extends PusherAppErrors
final case class PusherIncomingDecodeError(error: IncomingDecodeError) extends PusherAppErrors
final case class PusherMessageFilteredOutError(error: MessageFilteredOutError) extends PusherAppErrors
final case class PusherCouldNotCreateOutgoingMessage(error: CouldNotCreateOutgoingMessage) extends PusherAppErrors
final case class PusherOutgoingEncodeError(error: OutgoingEncodeError) extends PusherAppErrors
final case class PusherSendError(error: SendError) extends PusherAppErrors

sealed trait MessageReceiveError
final case class ReceiveMessageError(reason: String, error: Option[Throwable]) extends MessageReceiveError
final case object ReceivedNoMessageError extends MessageReceiveError

final case class DeleteMessageError(reason: String, error: Option[Throwable])
final case class CouldNotLogError(reason: String, error: Option[Throwable])
final case class IncomingDecodeError(reason: String, error: Option[Throwable])
final case class MessageFilteredOutError(reason: String, error: Option[Throwable])
final case class CouldNotCreateOutgoingMessage(reason: String, error: Option[Throwable])
final case class OutgoingEncodeError(reason: String, error: Option[Throwable])
final case class SendError(reason: String, error: Option[Throwable])