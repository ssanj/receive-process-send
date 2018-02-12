package net.ssanj.pusher.option2
package sender

final class CommonNotificationSender(config: Config, notClient: NotificationClient) extends NotificationSender {
  def sendNotification[A](payload: A,
        )(implicit encoder: Encode[A]): Either[NotificationSenderError, Seq[NotificationResult]] = {
    for {
      jsonPayload <- encoder.encode(payload).leftMap(NotificationSenderEncodeError)
      presult     <- notClient.post(jsonPayload).leftMap(NotificationSenderClientError)
    } yield presult
  }
}

