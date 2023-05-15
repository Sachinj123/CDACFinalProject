import { Avatar, Box, CssBaseline, Grid, TextField, Typography, Button, Paper, Link, CircularProgress, Backdrop, Snackbar } from "@material-ui/core";
import AccountBoxOutlined from '@material-ui/icons/AccountBoxOutlined';
import { useState } from "react";
import { Link as RouteLink, useHistory } from "react-router-dom";
import Copyright from '../components/copyright';
import useStyles from '../styles/pages/signup';
import axios from "../config/axios.config";
import { Alert } from "@material-ui/lab";

const SignupPage = (props) => {
  const classes = useStyles();
  const [backdrop, toggleBackdrop] = useState(false);
  const [form, handleForm] = useState({
    username: "",
    password: "",
    fullname: "",
    email: "",
    dob: ""
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
    axios.post("/account/signup", {...form})
      .then(res => {
        handleResponse(({open: true, severity: "success", message: "Account Created Successfully."}))
        return res;
      })
      .then(res => {
        setTimeout(() => {
          history.push("/signin");
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
            <AccountBoxOutlined />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
          <form className={classes.form} noValidate onSubmit={handleSubmit} method="post">
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              value={form?.email}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.email !== undefined}
              helperText={errors.email}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="username"
              label="Username"
              type="text"
              id="username"
              value={form?.username}
              onBlur={handleBlur}
              onChange={handleChange}
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
              onBlur={handleBlur}
              onChange={handleChange}
              error={errors.password !== undefined}
              helperText={errors.password}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="fullname"
              label="Fullname"
              type="text"
              id="fullname"
              value={form?.fullname}
              onBlur={handleBlur}
              onChange={handleChange}
              error={errors.fullname !== undefined}
              helperText={errors.fullname}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="dob"
              id="dob"
              label="Birthday"
              type="date"
              className={classes.textField}
              InputLabelProps={{
                shrink: true,
              }}
              value={form?.dob}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.dob !== undefined}
              helperText={errors.dob}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Create Account
            </Button>
            <Grid container>
              <Grid item xs>
                <Link component={RouteLink} to="/signin" variant="body2">
                  {"Already have an account? Sign In"}
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
