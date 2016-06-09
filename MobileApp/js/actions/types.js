'use strict';

var REQUEST_URL = 'http://ec2-54-153-27-207.us-west-1.compute.amazonaws.com:8083/tweets';

export function clicked() {
    return {
        type: 'CLICKED',
    };
}

export function updateTweets(data) {
    return {
        type: 'UPDATE_TWEETS',
        tweets: data,
    };
}

export function openTweet(tweet) {
    return {
        type: 'SET_ACTIVE_TWEET',
        tweet: tweet,
    }
}

export function loadTweets(data) {
    return (dispatch) => {
        fetch(REQUEST_URL)
            .then((response) => response.json())
            .then((responseData) => {
                dispatch(updateTweets(responseData.rows));
            })
            .done();
        // setTimeout(() =>
        //     dispatch(updateTweets([{text: 'Tweet One', author: 'Author 1'}, {text: 'Tweet Two', author: 'Author 2'}])),
        //     1000);
    };
}


