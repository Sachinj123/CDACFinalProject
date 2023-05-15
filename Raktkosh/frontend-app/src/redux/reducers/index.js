import { combineReducers } from 'redux';
import themeReducer from './theme';
import userDetailsReducer from './user_details';

export default combineReducers({
  theme: themeReducer,
  user: userDetailsReducer
});
