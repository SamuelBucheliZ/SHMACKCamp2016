* Run cassandra in a docker instance
```
docker run -p 9042:9042  cassandra:latest
```
* Create keyspace and table with cqlsh
```
create keyspace zuehlke WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
CREATE TABLE IF NOT EXISTS zuehlke.tweets(date timestamp, text text, PRIMARY KEY (date, text));
```
* Run AkkaTwitter (TweetsToCassandra)

