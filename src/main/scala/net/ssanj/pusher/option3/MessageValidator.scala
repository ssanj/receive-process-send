package net.ssanj.pusher.option3

trait MessageValidator[A] {
  def isValid(message: A): Boolean

  def validationError(message: A): String

  def validateMessage(message: A): Either[MessageError, Unit] = {
    if (isValid(message)) Right(()) else Left(ValidationError(validationError(message)))
  }
}