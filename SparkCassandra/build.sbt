

name := "SparkCassandra"

version := "1.0"

scalaVersion := "2.11.8"

val spark = "1.6.0"


libraryDependencies ++= Seq(

  "org.slf4j"                       %  "slf4j-simple"               % "1.7.21",
  //"com.datastax.cassandra"          %  "cassandra-driver-core"      % "3.0.2",
  "org.apache.spark"                %  "spark-core_2.11"            % spark,
  "org.apache.spark"                %  "spark-sql_2.11"            % spark,
  "com.datastax.spark"              %  "spark-cassandra-connector_2.11"  % "1.5.0"

)
