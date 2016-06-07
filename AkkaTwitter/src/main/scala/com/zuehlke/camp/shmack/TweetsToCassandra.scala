package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, Props}
import akka.stream.scaladsl.{Sink, Source}
import com.zuehlke.camp.shmack.akkastream.{TweetFlow, TweetPublisher, TweetSubscriber}
import com.zuehlke.camp.shmack.cassandra.{CassandraOutput, ConfigCassandraCluster}
import com.zuehlke.camp.shmack.twitter.TwitterStreamWithActorTarget

object TweetsToCassandra extends App {
  // create source for tweet flow
  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])

  // create sink with cassandra output for tweet flow
  val cassandra = new ConfigCassandraCluster()
  val cassandraOutput = new CassandraOutput(cassandra)
  val sink = Sink.actorSubscriber(Props(new TweetSubscriber(cassandraOutput)))

  // set up tweet flow with given source and sink
  val tweetFlow: ActorRef = TweetFlow(source, sink)

  // connect twitter stream to tweet flow
  val twitterStream = TwitterStreamWithActorTarget(tweetFlow)

}