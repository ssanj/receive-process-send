package net.ssanj.pusher.option3

trait Json

trait Decode[A] {
  def decode(value: String): Either[DecodeError, A]
}

trait Encode[A] {
  def encode(value: A): Either[EncodeError, Json]
}
