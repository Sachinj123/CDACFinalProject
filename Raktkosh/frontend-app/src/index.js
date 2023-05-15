import React from 'react';
import ReactDOM from 'react-dom';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { basename } from './application';
import { BrowserRouter as Router } from 'react-router-dom';
import store from './redux';
import Application from './app';
import './styles/global.scss';

ReactDOM.render(
  // <React.StrictMode>
    <Provider store={store}>
      <Router basename={basename}>
        <Application />
      </Router>
    </Provider>
 //  </React.StrictMode>
 ,
  document.getElementById('app')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
