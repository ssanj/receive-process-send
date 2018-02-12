package net.ssanj.pusher.option2

trait MessageValidator {
  def validateMessage[A](message: A): Either[MessageValidationError, Unit]
}