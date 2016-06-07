package com.zuehlke.camp.shmack

import akka.actor.ActorRef
import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{FilterQuery, StatusListener, TwitterStream, TwitterStreamFactory}

object TwitterStreamWithActorTarget {

  def filterQuery = {
    // TODO: Create a nice query
    val filter = new FilterQuery()
    filter.language("de,en")
    filter.track("a")

    filter
  }

  def listener(target: ActorRef) = new TweetToActorForwarder(target)

  def apply(target: ActorRef) : TwitterStream = TwitterStreamWithActorTarget(filterQuery, listener(target))

  def apply(query : FilterQuery, listener: StatusListener) : TwitterStream = {
    val config = ConfigFactory.load()

    val consumerKey = config.getString("akkatwitter.twitter.consumerKey")
    val consumerSecret = config.getString("akkatwitter.twitter.consumerSecret")
    val accessToken = config.getString("akkatwitter.twitter.accessToken")
    val accessTokenSecret = config.getString("akkatwitter.twitter.accessTokenSecret")

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val twitterStream = new TwitterStreamFactory(cb.build()).getInstance()

    twitterStream.addListener(listener)
    twitterStream.filter(query)

    twitterStream
  }
}
