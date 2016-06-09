# Cassandra

## Set up Cassandra locally using Docker

Start a Cassandra docker instance (see https://hub.docker.com/_/cassandra/ )
```
docker run -p 9042:9042 --name cassandra-test -d cassandra:latest
```
and a ```cqlsh``` in another docker container
```
docker run -it --link cassandra-test:cassandra --rm cassandra cqlsh cassandra

```

## Prepare Cassandra for tweets

1. Create the keyspace
```
CREATE KEYSPACE IF NOT EXISTS zuehlke WITH replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
```

2. Check the keyspace was created

```
DESCRIBE keyspaces;
``` 

3. Create a table for the tweets
```
CREATE TABLE IF NOT EXISTS zuehlke.tweets(date timestamp, text text, PRIMARY KEY (date, text));
```

## Cassandra sample commands

- Insert data into table
```
INSERT INTO zuehlke.tweets(date,text) VALUES('2016-06-06 14:52','This is a sample tweet');
```
- Have a look at all data in table
```
SELECT * FROM zuehlke.tweets;
```

