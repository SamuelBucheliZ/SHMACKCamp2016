package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Sink, Source}

object TweetsToCassandra extends App {

  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val sink = ??? // TODO: Create TweetWriter here
  val tweetFlow: ActorRef = TweetFlow(source, sink)

  val twitterStream = TwitterStreamWithActorTarget(tweetFlow)

}