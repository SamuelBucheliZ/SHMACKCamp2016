package com.zuehlke.camp.shmack.akkastream

import akka.actor.ActorLogging
import akka.stream.actor.ActorSubscriberMessage.{OnComplete, OnError, OnNext}
import akka.stream.actor.{ActorSubscriber, OneByOneRequestStrategy, RequestStrategy}
import com.zuehlke.camp.shmack.{Tweet, TweetOutput}

class TweetSubscriber(output: TweetOutput) extends ActorSubscriber with ActorLogging {

  def receive: Receive = {
    case OnNext(tweet@Tweet(_, _)) =>
      log.debug("Got tweet {}", tweet)
      output.save(tweet)
    case OnComplete =>
      log.info("No more tweets left")
    case OnError(cause) =>
      log.error(cause, "Error in tweet flow")
  }

  override protected def requestStrategy: RequestStrategy = OneByOneRequestStrategy // TODO: Figure out what to use here
}
