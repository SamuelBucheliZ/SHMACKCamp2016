package com.zuehlke.camp.shmack.twitter

import akka.actor.ActorRef
import com.zuehlke.camp.shmack.Tweet
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}
import scala.collection.JavaConverters._


class TweetToActorForwarder(target: ActorRef) extends StatusListener {
  override def onStatus(status: Status): Unit = {

    if (status.getCreatedAt != null)
      target ! Tweet(status.getCreatedAt, status.getText, status.getFavoriteCount, status.getRetweetCount,
        status.getUser.getName, status.getHashtagEntities().map(e => e.getText).toList.asJava, status.getURLEntities().map(e => e.getText).toList.asJava,
        status.getUserMentionEntities().map(e => e.getText).toList.asJava)

  }

  override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

  override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

  override def onException(ex: Exception): Unit = {
    ex.printStackTrace()
  }

  override def onScrubGeo(lat: Long, longitude: Long) = {}

  override def onStallWarning(stallWarning: StallWarning): Unit = {}
}
