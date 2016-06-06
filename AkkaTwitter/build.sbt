import sbtassembly.MergeStrategy

name := "camp2016shmackakkatwitter"

organization := "zuehlke"

version := "1.0"

scalaVersion := "2.10.5"

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-cp", artifactTargetPath, "com.zuehlke.camp.shmack.TweetsToStdout")
  }
}

imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"${organization.value}/${name.value}:latest")
)

buildOptions in docker := BuildOptions(cache = false)

val spark = "1.6.0"

libraryDependencies ++= Seq(

  "org.twitter4j"                   %  "twitter4j-stream"          % "4.0.4",
//  "com.datastax.cassandra"          %  "cassandra-driver-core"     % "2.1.2",
  "com.softwaremill.reactivekafka"  %% "reactive-kafka-core"       % "0.8.2",
  "com.typesafe.akka"               %% "akka-stream-experimental"  % "2.0.4"

)


//some exclusions and merge strategies for assembly
excludeDependencies ++= Seq(
  "org.spark-project.spark" % "unused"

)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) => MergeStrategy.last
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}