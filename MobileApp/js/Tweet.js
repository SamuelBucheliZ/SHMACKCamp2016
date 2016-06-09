'use strict';

import React, {
    Component,
} from 'react';
import {
    Text,
    View,
    StyleSheet,
    TouchableHighlight,
    Image,
} from 'react-native';

export default class Tweet extends Component {

    render() {
        let author = this.props.tweet.author ? this.props.tweet.author : 'Unknown';

        return (
            <TouchableHighlight onPress={() => this.props.navigator.pop()}>
                <View style={styles.container}>
                    <Image
                        style={styles.icon}
                        source={require('../img/ic_person_outline_black.png')} />
                    <View style={styles.listContent}>
                        <Text style={styles.author}>@{author} </Text>
                        <Text style={styles.text}>{this.props.tweet.text}</Text>
                    </View>
                </View>
            </TouchableHighlight>
        )
    }
}

var styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#F5FCFF',
        paddingLeft: 16,
        paddingRight: 16,
        paddingTop: 8,
    },
    listContent: {
        flex: 1,
        paddingLeft: 16,
    },
    text: {
        fontSize: 16,
        color: '#00000087',
    },
    author: {
        fontSize: 14,
        color: '#00000054',
    },
    icon: {
        width: 40,
        height: 40,
    },
});


