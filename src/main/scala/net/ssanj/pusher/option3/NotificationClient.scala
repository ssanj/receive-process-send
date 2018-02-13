package net.ssanj.pusher.option3

trait NotificationClient {
  def post(payload: Json): Either[NotificationClientError, Seq[NotificationResult]]
}