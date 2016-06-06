package com.zuehlke.camp.shmack


import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util


import org.apache.kafka.common.serialization._

class TweetSerializer extends Serializer[Tweet] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def serialize(topic: String, data: Tweet): Array[Byte] = {

    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(data)
    oos.close()

    baos.toByteArray

  }

  override def close(): Unit = {}
}
