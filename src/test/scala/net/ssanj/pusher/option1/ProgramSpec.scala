package net.ssanj.pusher.option1

import org.scalatest.{Matchers, WordSpecLike, EitherValues}

final class ProgramSpec extends Matchers with WordSpecLike with EitherValues{

  trait TestQ
  trait TestLog

  implicit val successQueue = new Queue[TestQ] {

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

  import scala.collection.mutable.ListBuffer
  val buffer = ListBuffer[String]()


  implicit val log = new Log[TestLog] {

    def logInfo(message: String): CouldNotLogErrorOr[Unit] = {
      buffer += message
      Right(())
    }

    def logError(message: String, error: Option[Throwable]): CouldNotLogErrorOr[Unit] = {
      buffer += (message + error.fold("")(t => s", ${t.getMessage}"))
      Right(())
    }
  }

  "A Program" should {
    "receive and delete a message" when {
      "supplied one" in {
        val config = Config("some url", List("api1", "api2"), List("2222", "3333"))
        val r = Program.processMessage[TestQ, TestLog](config)

        val result = r.right.value

        result.message should be (QMessage("somebody", "hashbrown", PHandle("you can't handle this")))
        result.deleteResult should be (DeleteResult("deleted!"))

        buffer should (have size 1)
        buffer(0) should be ("QMessage(somebody,hashbrown,PHandle(you can't handle this))")
      }
    }
  }
}

