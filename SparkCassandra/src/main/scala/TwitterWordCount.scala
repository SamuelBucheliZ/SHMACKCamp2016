import org.apache.spark.{SparkConf, SparkContext}
import com.datastax.spark.connector._

case class Tweet(date: java.util.Date, text: String)

object TwitterWordCount extends App {
  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("TwitterWordCount")
    .set("spark.cassandra.connection.host", "localhost")
  val sc = new SparkContext(conf)

  val stopWordsInputStream = getClass.getResourceAsStream("/stopWords.txt")
  val stopWords =  scala.io.Source.fromInputStream(stopWordsInputStream).getLines().filter(line => !line.startsWith("#")).toSet
  val stopWordsBroadcast = sc.broadcast(stopWords)

  val tweetsTable = sc.cassandraTable[Tweet]("zuehlke", "tweets")

  tweetsTable
    .map(tweet => tweet.text)
    .flatMap(text => text.split("\\s"))
    .map(word => word.filter(Character.isLetter).toLowerCase)
    .filter(word => !word.isEmpty && !stopWordsBroadcast.value.contains(word))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
    .sortBy(tuple => tuple._2, ascending = false)
    .saveAsTextFile("test")
}
