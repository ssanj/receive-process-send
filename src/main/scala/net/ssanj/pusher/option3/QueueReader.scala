package net.ssanj.pusher.option3

trait QueueReader {

  def readFromQ(): Either[QError, QueueReadResult]
}