package net.ssanj.pusher.option1

trait PushSender[A] {
  def sendPush(config: Config, payload: Json): SendErrorOr[Seq[PushResult]]
}