# AkkaTwitter

The code in this module is based on
 - https://github.com/ftrossbach/intro-to-dcos for reading tweets and creating docker containers
 - https://github.com/eigengo/activator-akka-cassandra (see also http://www.lightbend.com/activator/template/activator-akka-cassandra ) for storing tweets in Cassandra

## Some notes on deploying Akka (Actors, and others)

> The code is [...] a straightforward Akka stream implementation. 
> Much more interesting is the deployment aspect. 
> The application is packaged as a docker container and deployed to a public docker registry [...]
> To deploy the app in our datacenter, we use DC/OS’ scheduler Marathon and the DC/OS CLI. 
> A new deployment in Marathon is defined in JSON. [...]
> In this file, we specify the Docker container we want to deploy, some variables like the number of CPUs or memory, and most importantly some environment variables for the container.

from
* https://blog.codecentric.de/en/2016/04/smack-stack-hands/

Build using
- ```sbt assembly``` for a fat jar
- ```sbt docker``` to get a docker image

## Running AkkaTwitter

Java properties that need to be set:
```
-Dakkatwitter.twitter.accessToken="..."
-Dakkatwitter.twitter.accessTokenSecret="..."
-Dakkatwitter.twitter.consumerKey="..."
-Dakkatwitter.twitter.consumerSecret="..."
```

Java properties that can be set (and currently used default values):
```
-Dakkatwitter.cassandra.hosts.0="localhost"
-Dakkatwitter.cassandra.port=9042
-Dakkatwitter.cassandra.keyspace="zuehlke"
```

Alternatively, you can also set them via ```application.conf```

 
 
