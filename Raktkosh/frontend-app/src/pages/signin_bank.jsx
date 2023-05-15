import { 
  Avatar, Box, Checkbox,
  Button, Paper, Backdrop,
  CircularProgress, Snackbar,
  Grid, TextField, Typography,
  CssBaseline, FormControlLabel,Link
} from "@material-ui/core";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import { useState } from "react";
import { useDispatch } from "react-redux";
import { Link as RouteLink, useHistory } from "react-router-dom";
import axios from "../config/axios.config";
import Copyright from '../components/copyright';
import useStyles from '../styles/pages/signup';
import { setUserDetails } from '../redux/actions';
import { Alert } from "@material-ui/lab";

const SignupPage = (props) => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const [backdrop, toggleBackdrop] = useState(false);
  const [form, handleForm] = useState({
    username: "",
    password: ""
  });
  const [errors, handleErrors] = useState({});
  const [response, handleResponse] = useState({
    open: false
  });
  
  const history = useHistory();

  const handleChange = e => {
    handleForm(form => ({...form, [e.target.name]: e.target.value}))
  };

  const handleBlur = e => {
    if (e.target.value === "") {
      handleErrors(errors => ({...errors, [e.target.name]: `${e.target.name} is required.`}));
    }
    else {
      handleErrors(errors => {
        let error =  {...errors};
        delete error[e.target.name];
        return error;
      });
    }
  };

  const validateForm = () => {
    return (Object.keys(form).filter(field => form[field] === "").length === 0 && Object.keys(errors).length === 0);
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (!validateForm()) {
      return;
    }
    toggleBackdrop(true);
    handleResponse(({open: false}));
    axios.post("/account/signin", {...form})
      .then(res => {
        handleResponse(({open: true, severity: "success", message: "Authentication Successful."}))
        return res.data;
      })
      .then(data => {
        let {fullname, email, username, id, enabled, authorities: [...authority] } = data;
        authority = authority[0].authority;
        dispatch(setUserDetails({fullname, email, username, id, enabled, authority }));
        return data;
      })
      .then(data => {
        localStorage.setItem("token", data.token);
        setTimeout(() => {
          history.push("/home");
        }, 2000);
      })
      .catch(error => {
        if (!error.response) {
          handleResponse(({open: true, severity: "error", message: "Network Error"}));
        }
        else {
          handleResponse(({open: true, severity: "error", message: error.response.data.message}));
        }
      })
      .finally(() => toggleBackdrop(false));
  };
  
  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Backdrop className={classes.backdrop} open={backdrop}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>
      <Grid item xs={false} sm={4} md={7} className={classes.image} />
      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <div className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <form className={classes.form} noValidate onSubmit={handleSubmit} method="post">
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="username"
              label="Username"
              type="text"
              id="username"
              autoFocus
              value={form?.username}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.username !== undefined}
              helperText={errors.username}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              value={form?.password}
              autoComplete="current-password"
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.password !== undefined}
              helperText={errors.password}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link component={RouteLink} to="/recover" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link component={RouteLink} to="/signup" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
            <Box mt={5}>
              <Copyright />
            </Box>
          </form>
        </div>
      </Grid>
    </Grid>
  );
};

export default SignupPage;
