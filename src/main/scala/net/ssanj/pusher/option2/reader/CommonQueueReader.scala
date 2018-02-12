package net.ssanj.pusher.option2
package reader

final class CommonQueueReader(q: Queue, log: Log) extends QueueReader {
  def readFromQ(): Either[QueueReaderError, QueueReadResult] = {
    for {
       message <- q.receiveMessage().leftMap(ReaderError)
       _       <- log.info(s"received ${message}").leftMap(ReaderLogError)
       dr      <- q.deleteMessage(message).leftMap(ReaderError)
    } yield QueueReadResult(message, dr)
  }
}
