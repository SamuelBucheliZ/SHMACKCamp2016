import React, {
    Component,
} from 'react';
import {
    Text,
    View,
    StyleSheet,
    Navigator,
    BackAndroid,
} from 'react-native';
import TweetList from './TweetList';
import Tweet from './Tweet';

export const routes = {
    tweetList: 'TWEET_LIST',
    showTweet: 'SHOW_TWEET',
}

export default class AANavigator extends Component {
    navigator;

    constructor() {
        super();

        this.handleBackButton = this.handleBackButton.bind(this);
        this.renderScene = this.renderScene.bind(this);
    }

    componentDidMount() {
        BackAndroid.addEventListener('hardwareBackPress', this.handleBackButton);
    }

    componentWillUnmount() {
        BackAndroid.removeEventListener('hardwareBackPress', this.handleBackButton);
    }

    render() {
        return (
            <Navigator
                style={styles.container}
                initialRoute={{ name: 'Main' }}
                renderScene={ this.renderScene } />
        );
    }

    renderScene(route, navigator) {
        this.navigator = navigator;

        if(route.name === routes.showTweet) {
            
            console.log('Navigating to Tweet', route.tweet);
            return <Tweet navigator={navigator} tweet={route.tweet} />;
            
        } else {
            
            console.log('Navigating to TweetList');
            return <TweetList navigator={navigator}/>;
        }
    }

    handleBackButton() {
        if (this.navigator && this.navigator.getCurrentRoutes().length > 1) {
            this.navigator.pop();
            return true;
        }
    }
}

var styles = StyleSheet.create({
    container: {
        flex: 1,
    },
});