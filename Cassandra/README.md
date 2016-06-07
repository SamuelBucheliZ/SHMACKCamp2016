# Some sample Cassandra commands


```
CREATE KEYSPACE IF NOT EXISTS zuehlke WITH replication = {'class': 'NetworkTopologyStrategy', 'dc1': '3'};
```

```
describe keyspaces;
``` 

```
CREATE TABLE IF NOT EXISTS zuehlke.tweets(date timestamp, text text, PRIMARY KEY (date, text));
```

```
INSERT INTO zuehlke.tweets(date,text) VALUES('2016-06-06 14:52','This is a sample tweet');
```

```
SELECT * FROM zuehlke.tweets;
```
