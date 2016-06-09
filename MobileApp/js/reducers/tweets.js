'use strict';

import {
    ListView,
} from 'react-native';

const initialState = {
    loaded: false,
    list: new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2}),
    activeTweet: {},
};

export default function config(state = initialState, action) {
    if(action.type === 'UPDATE_TWEETS') {
        return {
            ...state,
            list: state.list.cloneWithRows(action.tweets),
            loaded: true,
        }
    } else if (action.type === 'SET_ACTIVE_TWEET') {
        console.log('setting active tweet', action.tweet);
        return {
            ...state,
            activeTweet: action.tweet,
        }
    }

    return state;
}
