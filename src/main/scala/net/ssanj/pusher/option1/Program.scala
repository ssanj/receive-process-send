package net.ssanj.pusher.option1

object Program {

  def processMessage2[A: Decode,
                      B: Encode,
                      Q: Queue,
                      PS: PushSender,
                      MV: MessageValidator,
                      ED: EncoderDecoder,
                      L: Log](config: Config): AppErrorOr[ProcessResult] = {
      for {
        message    <- I[Queue, Q].receiveMessage(config).leftMap(PusherMessageReceiveError)
        dr         <- I[Queue, Q].deleteMessage(config, message).leftMap(PusherDeleteMessageError)
        _          <- I[Log, L].logInfo(message.toString).leftMap(PusherCouldNotLogError)
        inMessage  <- I[EncoderDecoder, ED].convertToIncomingMessage[A](message).leftMap(PusherIncomingDecodeError)
        _          <- I[MessageValidator, MV].validateMessage[A](inMessage).leftMap(PusherMessageFilteredOutError) //only process matched id
        outMessage <- I[EncoderDecoder, ED].convertToOutgoingMessage[A, B](inMessage).leftMap(PusherCouldNotCreateOutgoingMessage)
        payload    <- I[EncoderDecoder, ED].encode[B](outMessage).leftMap(PusherOutgoingEncodeError)
        presult    <- I[PushSender, PS].sendPush(config, payload).leftMap(PusherSendError)  //must push to supplied api keys
      } yield ProcessResult(message, dr, presult)
  }


  def processMessage[Q: Queue,
                     L: Log](config: Config): AppErrorOr[ProcessResult] = {
      for {
        message    <- I[Queue, Q].receiveMessage(config).leftMap(PusherMessageReceiveError)
        dr         <- I[Queue, Q].deleteMessage(config, message).leftMap(PusherDeleteMessageError)
        _          <- I[Log, L].logInfo(message.toString).leftMap(PusherCouldNotLogError)
      } yield ProcessResult(message, dr, Nil)
  }
}