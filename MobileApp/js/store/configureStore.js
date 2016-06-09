'use strict';

import {
    applyMiddleware,
    createStore
} from 'redux';
import thunk from 'redux-thunk';
import promise from './promise';
import reducers from '../reducers';
import {
    loadTweets
} from '../actions/types';

var isDebuggingInChrome = __DEV__ && !!window.navigator.userAgent;

export default function configureStore() {
    const store = createStore(reducers, {}, applyMiddleware(thunk, promise));
    if (isDebuggingInChrome) {
        window.store = store;
    }

    store.dispatch(loadTweets());
    
    return store;
}
