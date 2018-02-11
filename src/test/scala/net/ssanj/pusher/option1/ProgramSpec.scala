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
}

