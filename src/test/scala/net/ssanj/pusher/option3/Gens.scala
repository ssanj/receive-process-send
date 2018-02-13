package net.ssanj.pusher.option3

import org.scalacheck._
import org.scalacheck.Arbitrary._

object Gens {
  //QMessage("message body2", "ABC1212", QHandle("567788"))
  def genQHandle: Gen[QHandle] = Gen.numStr.map(QHandle)

  def genQMessage: Gen[QMessage] = for {
    body   <- arbitrary[String]
    hash   <- Gen.identifier
    handle <- genQHandle
  } yield QMessage(body, hash, handle)

  def genDeleteResult: Gen[DeleteResult] = Gen.identifier.map(DeleteResult)

  def genThrowable: Gen[Throwable] = for {
    ex1       <- arbitrary[String]
    ex2       <- arbitrary[String]
    ex3       <- arbitrary[String]
    ex        <- Gen.oneOf(new RuntimeException(ex1),
                       new IllegalArgumentException(ex2),
                       new UnsupportedOperationException(ex3))
  } yield ex

  def genDeleteError: Gen[QError] = for {
    errorText <- Gen.identifier
    ex        <- genThrowable
    throwable <-  Gen.option(ex)
  } yield DeleteError(errorText, throwable)

  def genReceiveError: Gen[QError] = for {
    errorText <- Gen.identifier
    ex        <- genThrowable
    throwable <-  Gen.option(ex)
  } yield ReceiveError(errorText, throwable)
}