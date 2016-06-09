package com.zuehlke.camp.shmack

import java.util.Date

case class Tweet(date: Date, text: String, favoriteCount: Int, retweetCount: Int, username: String,
                 hashtags: Array[String], urls: Array[String], userMentions: Array[String])
