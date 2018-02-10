package net.ssanj.pusher

import scala.language.implicitConversions

package object option1 {
  implicit def toEitherOps[L, R](et: Either[L, R]): EitherOps[L, R] = new EitherOps[L, R] {
    def leftMap[L2](f: L => L2): Either[L2, R] = et.fold[Either[L2, R]](l => Left(f(l)), r => Right(r))
  }

  implicit def emptyDecoder[A](value: A): Decode[A] = new Decode[A] {}
  implicit def emptyEncoder[A](value: A): Encode[A] = new Encode[A] {}
}