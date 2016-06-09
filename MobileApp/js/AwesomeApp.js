'use strict';

import React, {
    Component,
} from 'react';
import {
    connect
} from 'react-redux';
import {
    View,
    Text,
    StyleSheet,
} from 'react-native';
import AANavigator from './AANavigator';
import LoadingScreen from './LoadingScreen';

class AwesomeApp extends Component {

    render() {
        if(!this.props.loaded) {
            return (
                <LoadingScreen />
            )
        } else {
            return (
                <View style={{flex: 1}}>
                    <View style={styles.header}>
                        <Text style={styles.title}>Newspaper</Text>
                    </View>
                    <AANavigator />
                </View>
            )
        }
    }

}

var styles = StyleSheet.create({
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        height: 56,
        elevation: 4,
        backgroundColor: '#FF5722',
        paddingLeft: 16,
        paddingRight: 16,
    },
    title: {
        fontSize: 20,
        fontFamily: 'sans-serif',
        fontWeight: 'bold',
        color: '#FFFFFF',
    },
});

function propsMap(store) {
    return {
        loaded: store.tweets.loaded,
    }
}


export default connect(propsMap)(AwesomeApp);

