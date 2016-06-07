package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Sink, Source}
import com.zuehlke.camp.shmack.akkastream.{TweetFlow, TweetPublisher, TweetSubscriber}
import com.zuehlke.camp.shmack.twitter.TwitterStreamWithActorTarget

// for simple testing
object TweetsToStdout extends App {

  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val sink = Sink.actorSubscriber[Tweet](Props(new TweetSubscriber(StdOutput)))

  val tweetFlow: ActorRef = TweetFlow(source, sink)

  val twitterStream = TwitterStreamWithActorTarget(tweetFlow)
}

object StdOutput extends TweetOutput {
  override def save(tweet: Tweet): Unit = println(tweet)
}