import { Backdrop, Button, Card, CardContent, CircularProgress, FormControl, FormControlLabel, Grid, InputLabel, MenuItem, Select, Snackbar, Switch, Typography } from "@material-ui/core";
import axios from "../../config/axios.config";
import { useState } from "react";
import {useSelector} from 'react-redux';
import useStyles from "../../styles/components/post-form";
import { Alert } from "@material-ui/lab";

const PostForm = props => {
  const classes = useStyles();
  const userInfo = useSelector(store => store.user);
  const [form, handleForm] = useState({
    type: "",
    antigen: "",
    postCategory: "RECEIVER"
  });
  const [errors, handleErrors] = useState({});
  const [backdrop, toggleBackdrop] = useState(false);
  const [response, handleResponse] = useState({
    open: false
  });


  const handleChange = e => {
    if (e.target.name === "postCategory") {
      handleForm(form => ({...form, [e.target.name]: e.target.value === "DONOR" ? "RECEIVER" : "DONOR"}));
      return;
    }
    handleForm(form => ({...form, [e.target.name]: e.target.value}));
  };

  const validateField = (e) => {
    if (e.target.value === "" || e.target.value === " ") {
      handleErrors(errors => ({...errors, [e.target.name]: true}));
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
    if (validateForm()) {
      toggleBackdrop(true);
      axios.post(`/post/add/${userInfo.id}`, form)
      .then(res => {
        handleResponse(({open: true, severity: "success", message: "Post Created Successfully."}))
        return res;
      })
      .catch(error => {
        console.log({...error});
        if (!error.response) {
          handleResponse(({open: true, severity: "error", message: "Network Error"}));
        }
        else {
          handleResponse(({open: true, severity: "error", message: error.response.data.message}));
        }
      })
      .finally(() => toggleBackdrop(false));
    }
  }

  return (
    <>
      <Backdrop className={classes.backdrop} open={backdrop}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>
      <Card className={classes.root} variant="outlined">
        <CardContent>
          <Typography className={classes.title} align="center" color="textSecondary" gutterBottom>
            Create a new post
          </Typography>
          <FormControl variant="outlined" fullWidth className={classes.formControl}>
            <InputLabel id="blood-type">Blood Type</InputLabel>
            <Select
              labelId="blood-type"
              id="blood-type-select"
              label="Blood Type"
              name="type"
              value={form?.type}
              onChange={handleChange}
              onBlur={validateField}
              error={errors?.type}
            >
              <MenuItem value=" ">
                <em>None</em>
              </MenuItem>
              {
                ["O", "A", "B", "AB"].map((type, idx) => (
                  <MenuItem value={type} key={idx}>{type}</MenuItem>
                ))
              }
            </Select>
          </FormControl>
          <FormControl variant="outlined" fullWidth className={classes.formControl}>
            <InputLabel id="antigen-type">Blood Type</InputLabel>
            <Select
              labelId="antigen-type"
              id="antigen-type-select"
              label="Antigen Type"
              name="antigen"
              value={form?.antigen}
              onChange={handleChange}
              onBlur={validateField}
              error={errors?.antigen}
            >
              <MenuItem value=" ">
                <em>None</em>
              </MenuItem>
              {
                ["POSITIVE", "NEGATIVE"].map((type, idx) => (
                  <MenuItem value={type} key={idx}>{type}</MenuItem>
                ))
              }
            </Select>
          </FormControl>
          <FormControl variant="outlined" fullWidth className={classes.formControl}>
            <Typography component="div">
              <Grid component="label" container alignItems="center" spacing={1}>
                <Grid item>Receiving</Grid>
                <Grid item>
                <FormControlLabel
                  control={
                    <Switch
                      name="postCategory"
                      value={form.postCategory}
                      checked={form.postCategory === "DONOR"}
                      onChange={handleChange}
                    />
                  }
                  label="Donating"
                />
                </Grid>
              </Grid>
            </Typography>
          </FormControl>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={handleSubmit}
          >
            Post
          </Button>
        </CardContent>
      </Card>
    </>
  );
};

export default PostForm;
