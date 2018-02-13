package net.ssanj.pusher.option3
package sender

final class CommonNotificationSender(config: Config, notClient: NotificationClient) extends NotificationSender {
  def sendNotification[A](payload: A,
        )(implicit encoder: Encode[A]): Either[NotificationClientError, Seq[NotificationResult]] = {
    for {
      jsonPayload <- encoder.encode(payload)
      presult     <- notClient.post(jsonPayload)
    } yield presult
  }
}

