package net.ssanj.pusher.option3

trait MessageProcessor {
  def process[A: Decode, B](message: QMessage, validator: MessageValidator[A], f: A => B): Either[MessageError, B]
}