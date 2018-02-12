package net.ssanj.pusher.option2

trait NotificationClient {
  def post(payload: Json): Either[NotificationClientError, Seq[NotificationResult]]
}