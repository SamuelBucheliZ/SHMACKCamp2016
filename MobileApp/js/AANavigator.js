import React, {
    Component,
} from 'react';
import {
    Text,
    View,
    StyleSheet,
    Navigator,
} from 'react-native';
import TweetList from './TweetList';
import Tweet from './Tweet';

export const routes = {
    tweetList: 'TWEET_LIST',
    showTweet: 'SHOW_TWEET',
}

export default class AANavigator extends Component {
    render() {
        return (
            <Navigator
                style={styles.container}
                initialRoute={{ name: 'Main' }}
                renderScene={ this.renderScene } />
        );
    }

    renderScene(route, navigator) {
        if(route.name === routes.showTweet) {
            
            console.log('Navigating to Tweet', route.tweet);
            return <Tweet navigator={navigator} tweet={route.tweet} />;
            
        } else {
            
            console.log('Navigating to TweetList');
            return <TweetList navigator={navigator}/>;
        }
    }
}

var styles = StyleSheet.create({
    container: {
        flex: 1,
    },
});