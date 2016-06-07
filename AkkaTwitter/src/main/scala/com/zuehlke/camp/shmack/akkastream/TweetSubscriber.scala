package com.zuehlke.camp.shmack.akkastream

import akka.actor.ActorLogging
import akka.stream.actor.ActorSubscriberMessage.{OnComplete, OnError, OnNext}
import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy, RequestStrategy}
import com.zuehlke.camp.shmack.{Tweet, TweetOutput}

class TweetSubscriber(output: TweetOutput) extends ActorSubscriber with ActorLogging {

  override def receive: Receive = {
    case OnNext(next) =>
      handleNext(next)
    case OnComplete =>
      log.info("No more tweets left in flow")
      context.stop(self)
    case OnError(cause) =>
      log.error(cause, "Error in tweet flow")
  }

  def handleNext(message : Any) = message match {
    case tweet : Tweet =>
      log.debug("Got tweet {}", tweet)
      output.save(tweet)
    case other =>
      log.warning("Got unknown item {}", other)
  }

  override protected def requestStrategy: RequestStrategy = OneByOneRequestStrategy // TODO: Figure out what to best use here
}
