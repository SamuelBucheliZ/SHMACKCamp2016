package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Sink, Source}
import com.zuehlke.camp.shmack.akkastream.{TweetFlow, TweetPublisher, TweetSubscriber}
import com.zuehlke.camp.shmack.cassandra.{CassandraOutput, ConfigCassandraCluster}
import com.zuehlke.camp.shmack.twitter.TwitterStreamWithActorTarget

object TweetsToCassandra extends App {

  val cassandra = new ConfigCassandraCluster()
  val cassandraOutput = new CassandraOutput(cassandra)
  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val sink = Sink.actorSubscriber(Props(new TweetSubscriber(cassandraOutput)))
  val tweetFlow: ActorRef = TweetFlow(source, sink)

  val twitterStream = TwitterStreamWithActorTarget(tweetFlow)

}