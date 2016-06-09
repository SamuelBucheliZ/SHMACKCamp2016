package com.zuehlke.camp.shmack

import java.util.Date
import java.util.List

case class Tweet(date: Date, text: String, favoriteCount: Integer, retweetCount: Integer, username: String,
                 hashtags: List[String], urls: List[String], userMentions: List[String])
