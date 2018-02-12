package net.ssanj.pusher.option2

trait NotificationSender {
  def sendNotification[A](payload: A)(implicit encoder: Encode[A]): Either[NotificationSenderError, Seq[NotificationResult]]
}
