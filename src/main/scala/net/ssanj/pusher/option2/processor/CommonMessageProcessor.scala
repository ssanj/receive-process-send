package net.ssanj.pusher.option2
package processor

final class CommonMessageProcessor(config: Config) {
  def process[A, B](qm: QMessage, validator: MessageValidator, f: A => B)(
    implicit decoder: Decode[A]): Either[MessageProcessingError, B] = {
      for {
        in  <- decoder.decode(qm.body).leftMap(MessageProcessingDecodeError)
        _   <- validator.validateMessage[A](in).leftMap(MessageProcessingValidationError)
      } yield f(in)
  }
}