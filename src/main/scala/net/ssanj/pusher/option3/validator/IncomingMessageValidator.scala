package net.ssanj.pusher.option3
package validator

final class IncomingMessageValidator extends MessageValidator[InputMessage] {

  def isValid(message: InputMessage): Boolean = message.id % 1000 == 0

  def validationError(message: InputMessage): String =
    s"Incoming id is not divisible by 1000: ${message.id}"
}