package net.ssanj.pusher.option1

trait MessageValidator[A] {
  def validateMessage[B](message: B): MessageFilteredOutErrorOr[Unit]
}