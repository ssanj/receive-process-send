package net.ssanj.pusher

import scala.language.implicitConversions
import scala.language.higherKinds

package object option1 {
  implicit def toEitherOps[L, R](et: Either[L, R]): EitherOps[L, R] = new EitherOps[L, R] {
    def leftMap[L2](f: L => L2): Either[L2, R] = et.fold[Either[L2, R]](l => Left(f(l)), r => Right(r))
  }

  type MessageReceiveErrorOr[A]           = Either[MessageReceiveError, A]
  type DeleteMessageErrorOr[A]            = Either[DeleteMessageError, A]
  type CouldNotLogErrorOr[A]              = Either[CouldNotLogError, A]
  type IncomingDecodeErrorOr[A]           = Either[IncomingDecodeError, A]
  type MessageFilteredOutErrorOr[A]       = Either[MessageFilteredOutError, A]
  type CouldNotCreateOutgoingMessageOr[A] = Either[CouldNotCreateOutgoingMessage, A]
  type OutgoingEncodeErrorOr[A]           = Either[OutgoingEncodeError, A]
  type SendErrorOr[A]                     = Either[SendError, A]
  type AppErrorOr[A]                      = Either[PusherAppErrors, A]


  implicit def I[TC[_], T](implicit tc: TC[T]): TC[T] = tc

  // implicit def emptyDecoder[A](value: A): Decode[A] = new Decode[A] {}
  // implicit def emptyEncoder[A](value: A): Encode[A] = new Encode[A] {}
}