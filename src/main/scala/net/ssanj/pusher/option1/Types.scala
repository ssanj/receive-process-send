package net.ssanj.pusher.option1

import QMessageTypes._
import Model._
import scala.language.implicitConversions

object Types {

  trait Json

  trait Decode[A]

  trait Encode[A]

  sealed trait PusherAppErrors
  final case class PusherMessageReceiveError(reason: String, error: Option[Throwable]) extends PusherAppErrors

    sealed trait MessageReceiveError
    final case class ReceiveMessageError(reason: String, error: Option[Throwable]) extends MessageReceiveError
    final case object ReceivedNoMessageError extends MessageReceiveError

    type MessageReceiveErrorOr[A] = Either[MessageReceiveError, A]

  final case class PusherDeleteMessageError(error: DeleteMessageError) extends PusherAppErrors

    final case class DeleteMessageError(reason: String, error: Option[Throwable])

    type DeleteMessageErrorOr[A] = Either[DeleteMessageError, A]


  final case class PusherCouldNotLogError(error: CouldNotLogError) extends PusherAppErrors
    final case class CouldNotLogError(reason: String, error: Option[Throwable])

    type CouldNotLogErrorOr[A] = Either[CouldNotLogError, A]

  final case class PusherIncomingDecodeError(error: IncomingDecodeError) extends PusherAppErrors
    final case class IncomingDecodeError(reason: String, error: Option[Throwable])

    type IncomingDecodeErrorOr[A] = Either[IncomingDecodeError, A]

  final case class PusherMessageFilteredOutError(error: MessageFilteredOutError) extends PusherAppErrors
    final case class MessageFilteredOutError(reason: String, error: Option[Throwable])

    type MessageFilteredOutErrorOr[A] = Either[MessageFilteredOutError, A]

  final case class PusherCouldNotCreateOutgoingMessage(error: CouldNotCreateOutgoingMessage) extends PusherAppErrors
    final case class CouldNotCreateOutgoingMessage(reason: String, error: Option[Throwable])

    type CouldNotCreateOutgoingMessageOr[A] = Either[CouldNotCreateOutgoingMessage, A]

  final case class PusherOutgoingEncodeError(error: OutgoingEncodeError) extends PusherAppErrors
    final case class OutgoingEncodeError(reason: String, error: Option[Throwable])

    type OutgoingEncodeErrorOr[A] = Either[OutgoingEncodeError, A]

  final case class PusherSendError(error: SendError) extends PusherAppErrors
    final case class SendError(reason: String, error: Option[Throwable])

    type SendErrorOr[A] = Either[SendError, A]

  type ErrorOr[A] = Either[PusherAppErrors, A]


  trait EitherOps[L, R] {
    def leftMap[L2](f: L => L2): Either[L2, R]
  }

  implicit def toEitherOps[L, R](et: Either[L, R]): EitherOps[L, R] = new EitherOps[L, R] {
    def leftMap[L2](f: L => L2): Either[L2, R] = et.fold[Either[L2, R]](l => Left(f(l)), r => Right(r))
  }

  implicit def emptyDecoder[A](value: A): Decode[A] = new Decode[A] {}
  implicit def emptyEncoder[A](value: A): Encode[A] = new Encode[A] {}


  trait Program {

    def processMessage[A: Decode, B: Encode](config: Config): ErrorOr[ProcessResult] = {
        for {
          message    <- receiveMessage(config).leftMap(PusherMessageReceiveError)
          dr         <- deleteMessage(config, message).leftMap(PusherDeleteMessageError)
          _          <- log(message.toString, None).leftMap(PusherCouldNotLogError)
          inMessage  <- convertToIncomingMessage[A](message).leftMap(PusherIncomingDecodeError)
          _          <- validateMessage[A](inMessage).leftMap(PusherMessageFilteredOutError) //only process matched id
          outMessage <- convertToOutgoingMessage[A, B](inMessage).leftMap(PusherCouldNotCreateOutgoingMessage)
          payload    <- encode[B](outMessage).leftMap(PusherOutgoingEncodeError)
          presult    <- sendPush(config, payload).leftMap(PusherSendError)  //must push to supplied api keys
        } yield ProcessResult(message, dr, presult)
    }

    def validateMessage[A](message: A): MessageFilteredOutErrorOr[Unit]

    def convertToOutgoingMessage[A, B](inMessage: A): CouldNotCreateOutgoingMessageOr[B]

    def encode[A: Encode](outMessage: A): OutgoingEncodeErrorOr[Json]

    def convertToIncomingMessage[A: Decode](message: QMessage): IncomingDecodeErrorOr[A]

    def receiveMessage(config: Config): MessageReceiveErrorOr[QMessage]

    def deleteMessage(config: Config, message: QMessage): DeleteMessageErrorOr[DeleteResult]

    def sendPush(config: Config, payload: Json): SendErrorOr[Seq[PushResult]]

    def log(message: String, error: Option[Throwable]): CouldNotLogErrorOr[Unit]
  }
}