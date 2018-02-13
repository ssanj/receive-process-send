package net.ssanj.pusher.option3
package reader

final class CommonQueueReader(q: Queue, log: Log) extends QueueReader {
  def readFromQ(): Either[QError, QueueReadResult] = {
    for {
       message <- q.receiveMessage()
       _ = log.info(s"received ${message}")
       dr      <- q.deleteMessage(message)
    } yield QueueReadResult(message, dr)
  }
}
