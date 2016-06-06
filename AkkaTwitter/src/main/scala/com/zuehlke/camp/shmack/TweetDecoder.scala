package com.zuehlke.camp.shmack

import java.io.{ByteArrayInputStream, ObjectInputStream}

import kafka.serializer.Decoder
import kafka.utils.VerifiableProperties

class TweetDecoder(props: VerifiableProperties) extends Decoder[Tweet]{
  override def fromBytes(bytes: Array[Byte]): Tweet = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    ois.readObject().asInstanceOf[Tweet]

  }
}
