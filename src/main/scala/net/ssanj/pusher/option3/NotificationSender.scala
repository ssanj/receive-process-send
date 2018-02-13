package net.ssanj.pusher.option3

trait NotificationSender {
  def sendNotification[A](payload: A)(implicit encoder: Encode[A]): Either[NotificationClientError, Seq[NotificationResult]]
}
