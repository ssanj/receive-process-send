package net.ssanj.pusher.option1

final class TestQ extends Queue[TestQ] {

  var receiveMessageConfig:Config = _
  var deleteMessageConfig:Config = _
  var deleteMessageQMessage:QMessage = _

  def receiveMessage(config: Config): MessageReceiveErrorOr[QMessage] = {
    receiveMessageConfig = config
    Right(QMessage("somebody", "hashbrown", PHandle("you can't handle this")))
  }

  def deleteMessage(config: Config, message: QMessage): DeleteMessageErrorOr[DeleteResult] = {
    deleteMessageConfig = config
    deleteMessageQMessage = message
    Right(DeleteResult("deleted!"))
  }
}

sealed trait ReceiveErrorType
case object NoMessages extends ReceiveErrorType
case object ReceiveError extends ReceiveErrorType

final class TestQWithoutErrors(errorType: ReceiveErrorType, error: Option[Throwable]) extends Queue[TestQWithoutErrors] {

  var receiveMessageConfig:Config = _

  def receiveMessage(config: Config): MessageReceiveErrorOr[QMessage] = {
    receiveMessageConfig = config
    errorType match {
      case NoMessages => Left(ReceivedNoMessageError)
      case ReceiveError => Left(ReceiveMessageError("some receive exception message", error))
    }

  }

  def deleteMessage(config: Config, message: QMessage): DeleteMessageErrorOr[DeleteResult] = ???
}


final class TestLog extends Log[TestLog] {

  import scala.collection.mutable.ListBuffer
  val buffer = ListBuffer[String]()

  def logInfo(message: String): CouldNotLogErrorOr[Unit] = {
    buffer += message
    Right(())
  }

  def logError(message: String, error: Option[Throwable]): CouldNotLogErrorOr[Unit] = {
    buffer += (message + error.fold("")(t => s", ${t.getMessage}"))
    Right(())
  }
}