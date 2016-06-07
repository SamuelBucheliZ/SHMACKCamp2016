'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const cassandra = require('cassandra-driver');

//  Connect to the cassandra  cluster
var client = new cassandra.Client( { contactPoints : [ 'node-0.cassandra.mesos' ] } );
client.connect(function(err, result) {
    console.log('Connected.');
});

// Constants
const PORT = 18080;

// App
const app = express();
app.use(bodyParser.json());
app.set('json spaces', 2);

var getAllTweets = 'SELECT * FROM zuehlke.tweets'
app.get('/tweets', function (req, res) {
console.log('get the tweets');
  client.execute(getAllTweets, [ ], function(err, result) {
        if (err) {
            res.status(404).send({ msg : 'Tweets not found.' });
        } else {
            res.json(result);        }
    });
});

app.listen(PORT);
