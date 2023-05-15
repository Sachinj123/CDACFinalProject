import { useMemo } from 'react';
import { useSelector } from 'react-redux';
import { createTheme } from '@material-ui/core';
import { darkTheme, lightTheme } from '../styles/theme';

const useTheme = () => {  
  const themeType = useSelector(store => store.theme);
  return useMemo (
    () => createTheme({
      palette: {
        ...(themeType ? darkTheme : lightTheme)
      }
    }),
    [themeType]
  );
};

export default useTheme;
