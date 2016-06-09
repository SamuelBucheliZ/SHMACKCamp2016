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

app.get('/tweets', function (req, res) {
  var limit = 100;
  if(req.query && req.query.limit){
  	limit = req.query.limit;
  }
  console.log('Getting ' + limit + ' tweets');
  client.execute('SELECT * FROM zuehlke.tweets limit ' + limit, function(err, result) {
        if (err) {
            res.status(404).send({ msg : err });
        } else {
            res.json(result);        }
    });
});

app.listen(PORT);
