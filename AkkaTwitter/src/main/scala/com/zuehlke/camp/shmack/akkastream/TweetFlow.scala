package com.zuehlke.camp.shmack.akkastream

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.zuehlke.camp.shmack.Tweet

object TweetFlow {

  implicit val actorSystem = ActorSystem("actorSystem")
  implicit val materializer = ActorMaterializer()

  def apply(source: Source[Tweet, ActorRef], sink: Sink[Tweet, _]) : ActorRef = Flow[Tweet]
    .filter(tweet => tweet.date != null && tweet.text != null)
    .to(sink)
    .runWith(source)
}
