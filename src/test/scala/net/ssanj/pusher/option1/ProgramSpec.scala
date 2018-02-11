package net.ssanj.pusher.option1

import org.scalatest.{Matchers, WordSpecLike, EitherValues}

final class ProgramSpec extends Matchers with WordSpecLike with EitherValues{

  "A Program" should {
    "receive and delete a message" when {
      "supplied one" in {

        implicit val queue = new TestQ
        implicit val log = new TestLog

        val config = Config("some url", List("api1", "api2"), List("2222", "3333"))
        val r = Program.processMessage[TestQ, TestLog](config)

        val result = r.right.value

        val message = QMessage("somebody", "hashbrown", PHandle("you can't handle this"))

        result.message should be (message)
        result.deleteResult should be (DeleteResult("deleted!"))

        queue.receiveMessageConfig should be (config)
        queue.deleteMessageConfig should be (config)
        queue.deleteMessageQMessage should be (message)

        log.buffer should (have size 1)
        log.buffer(0) should be ("QMessage(somebody,hashbrown,PHandle(you can't handle this))")
      }
    }
  }

  "handle an empty message" when  {
    "a message is not available" in {
      implicit val queue = new TestQWithoutErrors(NoMessages, None)
      implicit val log = new TestLog

      val config = Config("some url", List("api1", "api2"), List("2222", "3333"))
      val r = Program.processMessage[TestQWithoutErrors, TestLog](config)

      val result = r.left.value
      result should be (PusherMessageReceiveError(ReceivedNoMessageError))

      queue.receiveMessageConfig should be (config)
      log.buffer should be ('empty)
    }
  }

  "handle a receive error" when  {
    "retrieving a message" in {
      val error = Option(new RuntimeException("some exception"))
      implicit val queue = new TestQWithoutErrors(ReceiveError, error)
      implicit val log = new TestLog

      val config = Config("some url", List("api1", "api2"), List("2222", "3333"))
      val r = Program.processMessage[TestQWithoutErrors, TestLog](config)

      val result = r.left.value
      result should be (PusherMessageReceiveError(
                          ReceiveMessageError("some receive exception message",
                                              error)))

      queue.receiveMessageConfig should be (config)
      log.buffer should be ('empty)
    }
  }
}

