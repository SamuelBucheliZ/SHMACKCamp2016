package com.zuehlke.camp.shmack.twitter

import akka.actor.ActorRef
import com.zuehlke.camp.shmack.Tweet
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}


class TweetToActorForwarder(target: ActorRef) extends StatusListener {
  override def onStatus(status: Status): Unit = {

    if (status.getCreatedAt != null)
      target ! Tweet(status.getCreatedAt, status.getText)

  }

  override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

  override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

  override def onException(ex: Exception): Unit = {
    ex.printStackTrace()
  }

  override def onScrubGeo(lat: Long, longitude: Long) = {}

  override def onStallWarning(stallWarning: StallWarning): Unit = {}
}
