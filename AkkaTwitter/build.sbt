import sbtassembly.MergeStrategy

name := "akkatwitter"

organization := "mhaspra"

version := "1.0"

scalaVersion := "2.10.5"

mainClass in assembly := Some("com.zuehlke.camp.shmack.TweetsToCassandra")

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-cp", artifactTargetPath, "com.zuehlke.camp.shmack.TweetsToCassandra")
  }
}

imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"${organization.value}/${name.value}:latest")
)

buildOptions in docker := BuildOptions(cache = false)

libraryDependencies ++= Seq(

  "org.slf4j"                       %  "slf4j-simple"                 % "1.7.21",
  "org.twitter4j"                   %  "twitter4j-stream"             % "4.0.4",
  "com.datastax.cassandra"          %  "cassandra-driver-core"        % "3.0.2",
  "org.xerial.snappy"               %  "snappy-java"                  % "1.1.2.6",
  "com.typesafe.akka"               %% "akka-stream-experimental"     % "2.0.4"

)


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) => MergeStrategy.last
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
