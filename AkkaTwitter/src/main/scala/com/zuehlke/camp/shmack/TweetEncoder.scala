package com.zuehlke.camp.shmack

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import kafka.serializer.Encoder

class TweetEncoder extends Encoder[Tweet] {

  override def toBytes(t: Tweet): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(t)
    oos.close()

    baos.toByteArray

  }
}
