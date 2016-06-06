package com.zuehlke.camp.shmack

import akka.actor.ActorRef
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}


class TwitterStatusListener(target: ActorRef) extends StatusListener{
  override def onStatus(status: Status): Unit = {

    println((status.getCreatedAt, status.getText))

    if(status.getCreatedAt != null)
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
