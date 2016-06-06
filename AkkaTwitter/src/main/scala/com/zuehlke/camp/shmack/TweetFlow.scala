package com.zuehlke.camp.shmack

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Future

object TweetFlow {

  implicit val actorSystem = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()


  def apply(source: Source[Tweet, ActorRef], sink: Sink[Tweet, Future[Unit]]) : ActorRef = Flow[Tweet]
    .filter(tweet => tweet.date != null && tweet.text != null)
    .to(sink)
    .runWith(source)
}
