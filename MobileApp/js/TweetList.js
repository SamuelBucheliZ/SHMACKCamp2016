'use strict';

import React, {
    Component,
} from 'react';
import {
    connect
} from 'react-redux';
import {
    Text,
    View,
    ListView,
    StyleSheet,
    TouchableHighlight,
} from 'react-native';
import {
    openTweet,
    back,
} from './actions/types';
import {
    routes
} from './AANavigator';


class TweetList extends Component {

    render() {
        if(this.props.activeTweet && this.props.activeTweet.text) {
            return (
                <View>
                    <Text onPress={this.props.back}>Show tweet</Text>
                </View>
            )
        }
        if(this.props.loaded) {
            return (
                <View style={styles.container}>
                    <ListView
                        dataSource={this.props.tweets.list}
                        renderRow={this.renderTweet.bind(this)}
                        contentContainerStyle={styles.listView}
                    />
                </View>
            );
        } else {
            return (
                <View style={styles.container}>
                    <Text>Loading...</Text>
                </View>
            );
        }
    }

    renderTweet(tweet) {
        let author = tweet.author ? tweet.author : 'Unknown';

        return (
            <TouchableHighlight onPress={() => this.props.navigator.push({name: routes.showTweet, tweet: tweet})}>
                <View style={styles.listItem}>
                    <Text style={styles.author}>@{author} </Text>
                    <Text style={styles.text}>{tweet.text}</Text>
                </View>
            </TouchableHighlight>
        )
    }
}

var styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'stretch',
        backgroundColor: '#F5FCFF',
    },
    listView: {
        flexDirection: 'column',
        backgroundColor: '#F5FCFF',
        paddingLeft: 16,
        paddingRight: 16,
        paddingTop: 8,
    },
    listItem: {
        paddingBottom: 8,
    },

    text: {
        fontSize: 16,
        color: '#00000087',
    },
    author: {
        fontSize: 14,
        color: '#00000054',
    },
});

function select(store) {
    console.log("store", store);
    return {
        loaded: store.tweets.loaded,
        tweets: store.tweets,
        activeTweet: store.tweets.activeTweet,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        openTweet: (tweet) => {
            dispatch(openTweet(tweet))
        },
        back: () => {
            dispatch(back())
        }
    }
}

export default connect(select, mapDispatchToProps)(TweetList);

