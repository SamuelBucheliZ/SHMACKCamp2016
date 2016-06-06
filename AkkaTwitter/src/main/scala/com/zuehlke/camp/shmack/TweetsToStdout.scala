package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Sink, Source}

object TweetsToStdout extends App {

  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val sink = Sink.foreach[Tweet](t => println(t))

  val tweetFlow: ActorRef = TweetFlow(source, sink)

  val twitterStream = TweetStream(tweetFlow)

}