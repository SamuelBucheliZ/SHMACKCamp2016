

name := "SparkCassandra"

version := "1.0"

scalaVersion := "2.11.8"

val spark = "1.6.0"


libraryDependencies ++= Seq(

  "org.slf4j"                       %   "slf4j-simple"              % "1.7.21",
  "org.apache.spark"                %%  "spark-core"                % spark,
  "org.apache.spark"                %%  "spark-sql"                 % spark,
  "com.datastax.spark"              %%  "spark-cassandra-connector" % "1.5.0"

)
