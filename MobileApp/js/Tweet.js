'use strict';

import React, {
    Component,
} from 'react';
import {
    Text,
    View,
    StyleSheet,
    TouchableHighlight,
} from 'react-native';

export default class Tweet extends Component {

    render() {
        let author = this.props.tweet.author ? this.props.tweet.author : 'Unknown';

        return (
            <TouchableHighlight onPress={() => this.props.navigator.pop()}>
                <View>
                    <Text style={styles.author}>@{author} </Text>
                    <Text style={styles.text}>{this.props.tweet.text}</Text>
                </View>
            </TouchableHighlight>
        )
    }
}

var styles = StyleSheet.create({
    text: {
        fontSize: 16,
        color: '#00000087',
    },
    author: {
        fontSize: 14,
        color: '#00000054',
    },
});


