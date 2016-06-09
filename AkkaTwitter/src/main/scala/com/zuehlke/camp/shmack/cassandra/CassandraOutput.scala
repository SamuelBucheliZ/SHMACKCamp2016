package com.zuehlke.camp.shmack.cassandra

import com.zuehlke.camp.shmack.{Tweet, TweetOutput}

class CassandraOutput(cassandra: CassandraCluster) extends TweetOutput {
  val session = cassandra.cluster.connect(cassandra.keyspace)
  val preparedStatement = session.prepare("INSERT INTO tweets(date, text, favoriteCount, retweetCount, " +
    "username, hashtags, urls, userMentions) VALUES (?, ?, ?, ?, ?, ?, ?, ?);")

  override def save(tweet: Tweet): Unit = session.executeAsync(preparedStatement.bind(tweet.date, tweet.text,
    tweet.favoriteCount, tweet.retweetCount, tweet.username, tweet.hashtags, tweet.urls, tweet.userMentions))
}
