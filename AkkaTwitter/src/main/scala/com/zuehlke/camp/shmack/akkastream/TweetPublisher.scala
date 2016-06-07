package com.zuehlke.camp.shmack.akkastream

import akka.actor.ActorLogging
import akka.stream.actor.ActorPublisher
import akka.stream.actor.ActorPublisherMessage.{Cancel, Request}
import com.zuehlke.camp.shmack.Tweet


class TweetPublisher extends ActorPublisher[Tweet] with ActorLogging {
  override def receive: Receive = {
    case tweet: Tweet =>
      log.debug("Got tweet {}", tweet)
      if (isActive && totalDemand > 0) {
        onNext(tweet)
      }
    case Cancel =>
      log.info("Stopping TweetPublisher")
      context.stop(self)
    case Request(r) =>
      log.debug("Got request {}", r)

  }
}