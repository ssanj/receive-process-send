package net.ssanj.pusher.option2

trait QueueReader {

  def readFromQ(): Either[QueueReaderError, QueueReadResult]
}