package com.zuehlke.camp.shmack.cassandra

import com.datastax.driver.core.{Cluster, ProtocolOptions}
import com.typesafe.config.ConfigFactory

trait CassandraCluster {
  def cluster: Cluster
  def keyspace: String
}

class ConfigCassandraCluster extends CassandraCluster {
  import scala.collection.JavaConversions._

  private val config = ConfigFactory.load()
  private val port = config.getInt("akkatwitter.cassandra.port")
  private val hosts = config.getStringList("akkatwitter.cassandra.hosts").toList

  lazy val cluster: Cluster =
    Cluster.builder().
      addContactPoints(hosts: _*).
      withCompression(ProtocolOptions.Compression.SNAPPY).
      withPort(port).
      build()

  lazy val keyspace: String = config.getString("akkatwitter.cassandra.keyspace")
}