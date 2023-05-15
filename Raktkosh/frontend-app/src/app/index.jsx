import { Button, CssBaseline } from "@material-ui/core";
import { ThemeProvider } from "@material-ui/styles";
import { Route, Switch, useLocation } from "react-router";
import { useSelector } from "react-redux";
import { switchTheme } from '../redux/actions';
import { useTheme } from "../hooks";
import axios from '../config/axios.config';
import SigninPage from "../pages/signin";
import SignupPage from "../pages/signup";
import MainPage from "../pages/main";
import VerificationPage from "../pages/verification";
import LandingPage from "../pages/landing";
import ForgotPassword from "../pages/forgotpassword";
import ResetPassword from "../pages/resetpassword";


const Application = props => {
  const location = useLocation();
  const theme = useTheme();

  const requestServer = () => {
    axios.get("/hello")
      .then(res => console.log(res))
      .catch(err => console.log(err));
  };
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Switch location={location}>
        <Route exact path="/signin">
          <SigninPage />
        </Route>
        <Route exact path="/signup">
          <SignupPage />
        </Route>
        <Route exact path="/resetPassword/">
          <ResetPassword />
        </Route>
        <Route exact path="/forgotpassword">
          <ForgotPassword />
        </Route>
        
        <Route exact path="/verify/:token?">
          <VerificationPage />
        </Route>
        <Route path="/" exact>
          <LandingPage />
        </Route>
        <Route path="/">
          <MainPage />
        </Route>
        <Route path="*">
          <h1>Error</h1>
        </Route>
       
      </Switch>
    </ThemeProvider>
  );
};

export default Application;
