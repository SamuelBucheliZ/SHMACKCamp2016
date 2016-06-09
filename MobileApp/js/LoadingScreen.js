import React, {
    Component,
} from 'react';
import {
    Text,
    View,
    StyleSheet,
} from 'react-native';

export default class LoadingScreen extends Component {

    render() {
        return (
            <View style={styles.container}>
                <Text>Loading...</Text>
            </View>
        )
    }
}

var styles = StyleSheet.create({
    container: {
        flex: 1,
    },
});