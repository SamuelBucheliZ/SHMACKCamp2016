package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.softwaremill.react.kafka.{ProducerProperties, ReactiveKafka}
import com.typesafe.config.ConfigFactory
import org.reactivestreams.Subscriber
import twitter4j.{FilterQuery, TwitterStreamFactory}


object TweetsToKafka extends App {

  implicit val actorSystem = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()

  val reactiveKafka = new ReactiveKafka()

  val subscriber: Subscriber[Tweet] = reactiveKafka.publish(ProducerProperties(
    brokerList = config.getString("akkatwitter.kafka.host") + ":" + config.getString("akkatwitter.kafka.port"),
    topic = config.getString("akkatwitter.kafka.topic"),

    encoder = new TweetEncoder()
  ))

  val source = Source.actorPublisher[Tweet](Props[TweetPublisher])
  val ref: ActorRef = Flow[Tweet]
    .filter(tweet => tweet.date != null && tweet.text != null)
    .to(Sink.fromSubscriber(subscriber))
    .runWith(source)


  var listener = new TwitterStatusListener(ref)
  var twitterStream = new TwitterStreamFactory().getInstance()
  twitterStream.addListener(listener)
  var query = new FilterQuery()
  query.language("de,en")
  query.track("a")
  twitterStream.filter(query)


}