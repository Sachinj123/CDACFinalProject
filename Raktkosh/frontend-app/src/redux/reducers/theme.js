const themeReducer = (state = false, action) => {
  switch (action.type) {
    case 'SWITCH_THEME':
      return !state;
    default:
      return state;
  }
};

export default themeReducer;
