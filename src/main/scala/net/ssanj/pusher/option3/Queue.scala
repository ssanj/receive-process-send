package net.ssanj.pusher.option3

trait Queue {

  def receiveMessage(): Either[QError, QMessage]

  def deleteMessage(message: QMessage): Either[QError, DeleteResult]
}