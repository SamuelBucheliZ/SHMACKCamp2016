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
    TouchableNativeFeedback,
    Image,
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
        return (
            <ListView
                dataSource={this.props.tweets.list}
                renderRow={this.renderTweet.bind(this)}
                contentContainerStyle={styles.listView}
            />
        );
    }

    renderTweet(tweet) {
        let author = tweet.username ? tweet.username : 'Unknown';

        return (
            <TouchableNativeFeedback
                onPress={() => this.props.navigator.push({name: routes.showTweet, tweet: tweet})}
                background={TouchableNativeFeedback.Ripple('#2196F3')}>
                <View style={styles.listItem}>
                    <Image
                        style={styles.icon}
                        source={require('../img/ic_person_outline_black.png')} />
                    <View style={styles.content}>
                        <Text style={styles.author}>@{author} </Text>
                        <Text style={styles.text} numberOfLines={1}>{tweet.text}</Text>
                        <View style={styles.divider} />
                    </View>
                </View>
            </TouchableNativeFeedback>
        )
    }
}

var styles = StyleSheet.create({
    listView: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: '#F5FCFF',
        paddingLeft: 16,
        paddingTop: 8,
    },
    listItem: {
        flex: 1,
        flexDirection: 'row',
        paddingBottom: 8,
    },
    divider: {
        flex: 1,
        height: 1,
        backgroundColor: '#00000012',
        marginTop: 8,
    },
    content: {
        flex: 1,
        paddingLeft: 16,
    },
    text: {
        fontSize: 16,
        color: '#00000087',
        marginRight: 16,
    },
    author: {
        fontSize: 14,
        color: '#00000054',
        marginRight: 16,
    },
    icon: {
        width: 40,
        height: 40,
    },
});

function select(store) {
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

