package net.ssanj.pusher.option3

object Program {
    def handleMessage[A: Decode, B: Encode](
            qReader: QueueReader,
            mProcessor: MessageProcessor,
            validator: MessageValidator[A],
            tranform: A => B,
            notSender: NotificationSender): Either[PusherAppError, ProcessResult] = {
      for {
         qResult  <- qReader.readFromQ().leftMap(QueueError)
         message  <- mProcessor.process[A, B](qResult.message, validator, tranform).leftMap(MessageProcessingError)
         presult  <- notSender.sendNotification[B](message).leftMap(NotificationSendError)
      } yield ProcessResult(qResult, presult)
  }
}