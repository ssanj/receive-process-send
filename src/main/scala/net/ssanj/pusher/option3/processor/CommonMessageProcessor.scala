package net.ssanj.pusher.option3
package processor

final class CommonMessageProcessor(config: Config) {
  def process[A, B](qm: QMessage, validator: MessageValidator[A], f: A => B)(
    implicit decoder: Decode[A]): Either[MessageError, B] = {
      for {
        in  <- decoder.decode(qm.body)
        _   <- validator.validateMessage(in)
      } yield f(in)
  }
}