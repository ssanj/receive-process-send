package net.ssanj.pusher.option2

object Program {
    def handleMessage[A: Decode, B: Encode](
            qReader: QueueReader,
            mProcessor: MessageProcessor,
            validator: MessageValidator,
            tranform: A => B,
            notSender: NotificationSender): Either[PusherAppError, ProcessResult] = {
      for {
         qResult  <- qReader.readFromQ().leftMap(PusherQueueReaderError)
         message  <- mProcessor.process[A, B](qResult.message, validator, tranform).leftMap(PusherMessageProcessingError)
         presult  <- notSender.sendNotification[B](message).leftMap(PusherNotificationSendError)
      } yield ProcessResult(qResult, presult)
  }
}