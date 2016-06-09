# Spark

## Run Spark locally

1. Pre-conditions:
- [Cassandra started locally in Docker container](../Cassandra/README.md)
- [Cassandra filled with tweets](../AkkaTwitter/README.md)

2. Download Spark sources from http://spark.apache.org/

3. Build Spark from sources using ```make_distribution.sh```. Remember to set ```JAVA_HOME``` and ```SPARK_HOME```.

4. Build Cassandra Connector:
```
git clone https://github.com/datastax/spark-cassandra-connector.git
cd spark-cassandra-connector
sbt assembly
```

5. Start Spark shell with cassandra connector
```$SPARK_HOME/bin/spark-shell --jars $CASSANDRA_CONNECTOR/target/scala-2.10/connector-assembly-1.2.0-SNAPSHOT.jar```

6. Configure Spark Context in Spark shell

```
sc.stop
import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
val sc = new SparkContext("local", "test", conf)
```

7. Do a simple test:

```
import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
val rdd = sc.cassandraTable("system_schema","keyspaces")
println("Row count:" + rdd.count)
```

This should show you an exciting number, such as 5, depending on the number of your keyspaces.

8. If you want to play with the tweets, use:
```
val rdd = sc.cassandraTable("zuehlke","tweets")
```
