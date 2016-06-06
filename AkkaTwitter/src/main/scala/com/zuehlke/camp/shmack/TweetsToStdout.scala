package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{FilterQuery, TwitterStreamFactory}


object TweetsToStdout extends App {

  implicit val actorSystem = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()


  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val ref: ActorRef = Flow[Tweet]
    .filter(tweet => tweet.date != null && tweet.text != null)
    .to(Sink.foreach(x => println(x)))
    .runWith(source)

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

  var listener = new TwitterStatusListener(ref)
  var twitterStream = new TwitterStreamFactory(cb.build()).getInstance()
  twitterStream.addListener(listener)
  var query = new FilterQuery()
  query.language("de,en")
  query.track("a")
  twitterStream.filter(query)


}