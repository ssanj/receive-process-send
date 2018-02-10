package net.ssanj.pusher.option1

trait Queue[A] {

  def receiveMessage(config: Config): MessageReceiveErrorOr[QMessage]

  def deleteMessage(config: Config, message: QMessage): DeleteMessageErrorOr[DeleteResult]
}