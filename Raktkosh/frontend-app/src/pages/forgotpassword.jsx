import {
  Avatar, Box, Checkbox,
  Button, Paper, Backdrop,
  CircularProgress, Snackbar,
  Grid, TextField, Typography,
  CssBaseline, FormControlLabel, Link
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


const ForgotPassword = (props) => {
  const history = useHistory();
  const classes = useStyles();
  const dispatch = useDispatch();
  const [backdrop, toggleBackdrop] = useState(false);
  const [form, handleForm] = useState({
    username: "",
  });
  const [errors, handleErrors] = useState({});
  const [response, handleResponse] = useState({
    open: false
  });

  // const history = useHistory();

  const handleChange = e => {
    handleForm(form => ({ ...form, [e.target.name]: e.target.value }))
  };

  const handleBlur = e => {
    if (e.target.value === "") {
      handleErrors(errors => ({ ...errors, [e.target.name]: `${e.target.name} is required.` }));
    }
    else {
      handleErrors(errors => {
        let error = { ...errors };
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
    handleResponse(({ open: false }));
    axios.post("/account/forgotpassword", { ...form })
      .then(res => {
        handleResponse(({ open: true, severity: "success", message: "OTP Sent" }))
        return res.data;
      }, 2000)


      .catch(error => {
        if (!error.response) {
          handleResponse(({ open: true, severity: "error", message: "Network Error" }));
        }
        else {
          handleResponse(({ open: true, severity: "error", message: error.response.data.message }));
        }
      })
      .then(
        history.push("/resetpassword")
      )
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
            Submit
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


            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            >
              Submit
            </Button>
            <Grid container>
              <Grid item xs>
                <Link component={RouteLink} to="/signin" variant="body2">
                  Already have an account? Sign In
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

export default ForgotPassword;
