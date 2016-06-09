'use strict';

import React, {
    Component,
} from 'react';
import AwesomeApp from './AwesomeApp';
import {
    Provider
} from 'react-redux';
import configureStore from './store/configureStore';

export function setup() {

    class Root extends Component {
        constructor() {
            super();
            this.state = {
                store: configureStore(),
            };
        }
        render() {
            return (
                <Provider store={this.state.store}>
                    <AwesomeApp />
                </Provider>
            );
        }
    }

    return Root;
}

