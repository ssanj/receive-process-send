package net.ssanj.pusher.option2

trait MessageProcessor {
  def process[A: Decode, B](message: QMessage, validator: MessageValidator, f: A => B): Either[MessageProcessingError, B]
}