package net.ssanj.pusher.option3

import org.scalatest.{EitherValues, Matchers, WordSpecLike}
import net.ssanj.pusher.option3.reader.CommonQueueReader
import Gens._

final class CommonQueueReaderSpec extends Matchers with WordSpecLike with EitherValues {
  "CommonQueueReader" should {
    "read, log and delete message" when {
      "a message is on the queue" in {
        val qmessage  = genQMessage.sample.get
        val dresult   = genDeleteResult.sample.get
        val queue     = new SuccessTestQueue(qmessage, dresult) { }
        val log       = new BufferedLog
        val reader    = new CommonQueueReader(queue, log)

        val result = reader.readFromQ.right.value

        result.message should be (qmessage)
        result.deleteResult should be (dresult)

        log.buffer should (have size 1)
        log.buffer(0) should be ("received " + qmessage.toString)
      }
    }

    "return a receive error" when {
      "a message can't be received" in {
        val qmessage  = genQMessage.sample.get
        val dresult   = genDeleteResult.sample.get
        val error     = genReceiveError.sample.get

        val receiveFailingQ = new SuccessTestQueue(qmessage, dresult) {
          override def receiveMessage() = Left(error)
        }

        val log       = new BufferedLog
        val reader    = new CommonQueueReader(receiveFailingQ, log)

        val result = reader.readFromQ.left.value

        result should be (error)
        log.buffer should be ('empty)
      }
    }

    "return a no message error" when {
      "there are no messages on the queue" in {
        val qmessage  = genQMessage.sample.get
        val dresult   = genDeleteResult.sample.get

        val receiveFailingQ = new SuccessTestQueue(qmessage, dresult) {
          override def receiveMessage() =Left(NoMessageError)
        }

        val log       = new BufferedLog
        val reader    = new CommonQueueReader(receiveFailingQ, log)

        val result = reader.readFromQ.left.value

        result should be (NoMessageError)
        log.buffer should be ('empty)
      }
    }


    "return a delete error" when {
      "a message can't be deleted" in {
        val qmessage  = genQMessage.sample.get
        val dresult   = genDeleteResult.sample.get
        val error     = genDeleteError.sample.get

        val receiveFailingQ = new SuccessTestQueue(qmessage, dresult) {
          override def deleteMessage(message: QMessage) = Left(error)
        }

        val log       = new BufferedLog
        val reader    = new CommonQueueReader(receiveFailingQ, log)

        val result = reader.readFromQ.left.value

        result should be (error)
        log.buffer should (have size 1)
        log.buffer(0) should be ("received " + qmessage.toString)

      }
    }
  }
}