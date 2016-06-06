package com.zuehlke.camp.shmack

import akka.actor.Actor
import com.datastax.driver.core.Cluster

class TweetWriter(cluster: Cluster) extends Actor { // TODO: Make ActorSubscriber?
  val keyspace = "something" // TODO: keyspace?
  val session = cluster.connect(keyspace)
  val preparedStatement = session.prepare("INSERT INTO tweets(date, text) VALUES (?, ?);") // TODO: check actual Cassandra scheme

  def saveTweet(tweet: Tweet): Unit =
    session.executeAsync(preparedStatement.bind(tweet.date, tweet.text))

  def receive: Receive = {
    case tweets: List[Tweet] => tweets foreach saveTweet
    case tweet: Tweet        => saveTweet(tweet)
  }
}