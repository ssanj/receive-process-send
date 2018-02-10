package net.ssanj.pusher.option1

trait EncoderDecoder[T] {
  
  def convertToOutgoingMessage[A, B](inMessage: A): CouldNotCreateOutgoingMessageOr[B]

  def encode[A: Encode](outMessage: A): OutgoingEncodeErrorOr[Json]

  def convertToIncomingMessage[A: Decode](message: QMessage): IncomingDecodeErrorOr[A]

}