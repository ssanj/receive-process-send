package net.ssanj.pusher.option3

abstract class SuccessTestQueue(qmessage: QMessage, deleteResult: DeleteResult) extends Queue {

  def receiveMessage(): Either[QError, QMessage] = Right(qmessage)

  def deleteMessage(message: QMessage): Either[QError, DeleteResult] = Right(deleteResult)
}

final class BufferedLog extends Log {

  import scala.collection.mutable.ListBuffer
  val buffer = ListBuffer[String]()

  def info(message: String): Unit = {
    buffer += message
  }

  def error(message: String, error: Option[Throwable]): Unit = {
    buffer += (message + error.fold("")(t => s", ${t.getMessage}"))
  }
}