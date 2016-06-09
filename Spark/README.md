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

8. Simple word count:
```
case class Tweet(date: java.util.Date, text: String)
val tweetsTable = sc.cassandraTable[Tweet]("zuehlke","tweets")

tweetsTable.map(tweet => tweet.text)
           .flatMap(text => text.split("\\s"))
           .map(word => word.toLowerCase)
           .map(word => (word, 1))
           .reduceByKey(_ + _)
           .sortBy(tuple => tuple._2, ascending = false)
           .saveAsTextFile("test")
```

We also tried filtering with stopwords:
```
val stopWords = sc.textFile("stopWords.txt").collect().toSet
val stopWordsBroadcast = sc.broadcast(stopWords)

tweetsTable.map(tweet => tweet.text)
           .flatMap(text => text.split("\\s"))
           .map(word => word.toLowerCase)
           .filter(word => !stopWordsBroadcast.value.contains(word))
           .map(word => (word, 1))
           .reduceByKey(_ + _)
           .sortBy(tuple => tuple._2, ascending = false)
           .saveAsTextFile("testStopWords")
```
but this gave the following exception:
```
org.apache.spark.SparkException: Task not serializable
	at org.apache.spark.util.ClosureCleaner$.ensureSerializable(ClosureCleaner.scala:304)
	at org.apache.spark.util.ClosureCleaner$.org$apache$spark$util$ClosureCleaner$$clean(ClosureCleaner.scala:294)
	at org.apache.spark.util.ClosureCleaner$.clean(ClosureCleaner.scala:122)
	at org.apache.spark.SparkContext.clean(SparkContext.scala:2055)
	at org.apache.spark.rdd.RDD$$anonfun$filter$1.apply(RDD.scala:341)
	at org.apache.spark.rdd.RDD$$anonfun$filter$1.apply(RDD.scala:340)
	at org.apache.spark.rdd.RDDOperationScope$.withScope(RDDOperationScope.scala:150)
	at org.apache.spark.rdd.RDDOperationScope$.withScope(RDDOperationScope.scala:111)
	at org.apache.spark.rdd.RDD.withScope(RDD.scala:316)
	at org.apache.spark.rdd.RDD.filter(RDD.scala:340)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:49)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:58)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:60)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:62)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:64)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:66)
	at $iwC$$iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:68)
	at $iwC$$iwC$$iwC$$iwC$$iwC.<init>(<console>:70)
	at $iwC$$iwC$$iwC$$iwC.<init>(<console>:72)
	at $iwC$$iwC$$iwC.<init>(<console>:74)
	at $iwC$$iwC.<init>(<console>:76)
	at $iwC.<init>(<console>:78)
	at <init>(<console>:80)
	at .<init>(<console>:84)
	at .<clinit>(<console>)
	at .<init>(<console>:7)
	at .<clinit>(<console>)
	at $print(<console>)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.repl.SparkIMain$ReadEvalPrint.call(SparkIMain.scala:1065)
	at org.apache.spark.repl.SparkIMain$Request.loadAndRun(SparkIMain.scala:1346)
	at org.apache.spark.repl.SparkIMain.loadAndRunReq$1(SparkIMain.scala:840)
	at org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:871)
	at org.apache.spark.repl.SparkIMain.interpret(SparkIMain.scala:819)
	at org.apache.spark.repl.SparkILoop.org$apache$spark$repl$SparkILoop$$pasteCommand(SparkILoop.scala:825)
	at org.apache.spark.repl.SparkILoop$$anonfun$standardCommands$8.apply(SparkILoop.scala:345)
	at org.apache.spark.repl.SparkILoop$$anonfun$standardCommands$8.apply(SparkILoop.scala:345)
	at scala.tools.nsc.interpreter.LoopCommands$LoopCommand$$anonfun$nullary$1.apply(LoopCommands.scala:65)
	at scala.tools.nsc.interpreter.LoopCommands$LoopCommand$$anonfun$nullary$1.apply(LoopCommands.scala:65)
	at scala.tools.nsc.interpreter.LoopCommands$NullaryCmd.apply(LoopCommands.scala:76)
	at org.apache.spark.repl.SparkILoop.command(SparkILoop.scala:809)
	at org.apache.spark.repl.SparkILoop.processLine$1(SparkILoop.scala:657)
	at org.apache.spark.repl.SparkILoop.innerLoop$1(SparkILoop.scala:665)
	at org.apache.spark.repl.SparkILoop.org$apache$spark$repl$SparkILoop$$loop(SparkILoop.scala:670)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply$mcZ$sp(SparkILoop.scala:997)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply(SparkILoop.scala:945)
	at org.apache.spark.repl.SparkILoop$$anonfun$org$apache$spark$repl$SparkILoop$$process$1.apply(SparkILoop.scala:945)
	at scala.tools.nsc.util.ScalaClassLoader$.savingContextLoader(ScalaClassLoader.scala:135)
	at org.apache.spark.repl.SparkILoop.org$apache$spark$repl$SparkILoop$$process(SparkILoop.scala:945)
	at org.apache.spark.repl.SparkILoop.process(SparkILoop.scala:1059)
	at org.apache.spark.repl.Main$.main(Main.scala:31)
	at org.apache.spark.repl.Main.main(Main.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.spark.deploy.SparkSubmit$.org$apache$spark$deploy$SparkSubmit$$runMain(SparkSubmit.scala:731)
	at org.apache.spark.deploy.SparkSubmit$.doRunMain$1(SparkSubmit.scala:181)
	at org.apache.spark.deploy.SparkSubmit$.submit(SparkSubmit.scala:206)
	at org.apache.spark.deploy.SparkSubmit$.main(SparkSubmit.scala:121)
	at org.apache.spark.deploy.SparkSubmit.main(SparkSubmit.scala)
Caused by: java.io.NotSerializableException: org.apache.spark.SparkConf
Serialization stack:
	- object not serializable (class: org.apache.spark.SparkConf, value: org.apache.spark.SparkConf@22f0e329)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: conf, type: class org.apache.spark.SparkConf)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@4b5455c6)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@55f4d7be)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@2ac8f084)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@777a2b88)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@31ec9703)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@270cf3eb)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC@3b4675d3)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC@3dd3f237)
	- field (class: $iwC$$iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC@2f539c9b)
	- field (class: $iwC$$iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC, $iwC$$iwC$$iwC@29457ad2)
	- field (class: $iwC$$iwC, name: $iw, type: class $iwC$$iwC$$iwC)
	- object (class $iwC$$iwC, $iwC$$iwC@17143892)
	- field (class: $iwC, name: $iw, type: class $iwC$$iwC)
	- object (class $iwC, $iwC@72416c9a)
	- field (class: $line32.$read, name: $iw, type: class $iwC)
	- object (class $line32.$read, $line32.$read@48d05f98)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $VAL51, type: class $line32.$read)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@6dee647f)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, name: $outer, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC, $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC@33d3e850)
	- field (class: $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$anonfun$5, name: $outer, type: class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC)
	- object (class $iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$iwC$$anonfun$5, <function1>)
	at org.apache.spark.serializer.SerializationDebugger$.improveException(SerializationDebugger.scala:40)
	at org.apache.spark.serializer.JavaSerializationStream.writeObject(JavaSerializer.scala:47)
	at org.apache.spark.serializer.JavaSerializerInstance.serialize(JavaSerializer.scala:101)
	at org.apache.spark.util.ClosureCleaner$.ensureSerializable(ClosureCleaner.scala:301)
	... 63 more
```
Somehow, it seems, that the SparkConf ends up in the closure of something that that is sent to a node, but it is not serializable... We haven't figured out yet, how to solve this.
