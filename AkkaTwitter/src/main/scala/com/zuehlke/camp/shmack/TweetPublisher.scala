package com.zuehlke.camp.shmack

import akka.stream.actor.ActorPublisher
import akka.stream.actor.ActorPublisherMessage.{Cancel, Request}


class TweetPublisher extends ActorPublisher[Tweet] {
  override def receive: Receive = {
    case t: Tweet =>
      if (isActive && totalDemand > 0) {
        onNext(t)
      }
    case Cancel => context.stop(self)
    case Request(_) =>

  }
}