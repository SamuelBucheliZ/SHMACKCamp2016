import org.apache.spark.{SparkConf, SparkContext}
import com.datastax.spark.connector._

object SparkCassandra extends App {

  val conf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("SparkCassandra")
    //set Cassandra host address as your local address
    .set("spark.cassandra.connection.host", "localhost")
  val sc = new SparkContext(conf)
  //get table from keyspace and stored as rdd
  val rdd = sc.cassandraTable("zuehlke", "tweets")

  rdd.collect
    .map(row => row.get[String]("text")).foreach(println)


  sc.stop()
}


