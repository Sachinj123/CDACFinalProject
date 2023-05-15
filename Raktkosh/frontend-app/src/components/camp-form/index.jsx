import {
  Dialog, DialogTitle, DialogContent,
  DialogActions, TextField, Button, Slide, Grid, Backdrop, CircularProgress, Snackbar
} from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import { forwardRef, useState } from "react";
import axios from "../../config/axios.config";
import useStyles from "../../styles/components/blood-bank-form";

const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const CampForm = props => {
  const classes = useStyles();
  const [form, handleForm] = useState({});
  const [errors, handleErrors] = useState({});
  const [response, handleResponse] = useState({
    loading: false,
    open: false
  });


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

  const handleSubmit = () => {
    if (validateForm()) {
      handleResponse(res => ({...res, loading: true}));
      axios.post("/camp/add", form)
        .then(res => {
          handleResponse(res => ({...res, severity: "success", message: "Blood Donation Camp Added Successfully"}));
          handleForm({});
        })
        .catch(err => {
          handleResponse(res => ({...res, severity: "error", message: "Failed To Add Blood Donation Camp"}));
        })
        .finally(() => {
          handleResponse(res => ({...res, open: true, loading: false}));
        });

    }
    else{
      console.log("invalid form")
    }
  };

  return (
    <Dialog open={props.open} onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
      className={classes.root}
      TransitionComponent={Transition}
    >
      <Backdrop className={classes.backdrop} open={response.loading}>
        <CircularProgress color="inherit" />
      </Backdrop>

      
      <Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>

      <DialogTitle id="form-dialog-title">Add Blood Donation Camp</DialogTitle>
      <DialogContent>
        <Grid container spacing={1}>
          
          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="name"
              name="name"
              label="Name"
              type="text"
              fullWidth
              value={form?.name}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.name !== undefined}
              helperText={errors.name}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="email"
              name="email"
              label="Email"
              type="email"
              fullWidth
              value={form?.email}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.email !== undefined}
              helperText={errors.email}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="username"
              name="username"
              label="UserName"
              type="text"
              fullWidth
              value={form?.username}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.username !== undefined}
              helperText={errors.username}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="password"
              name="password"
              label="Password"
              type="text"
              fullWidth
              value={form?.password}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.password !== undefined}
              helperText={errors.password}
            />
          </Grid>

          <Grid item xs={12}>
          <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="campDate"
              id="campDate"
              label="CampDate"
              type="date"
               className={classes.textField}
              error={errors.campDate !== undefined}
              // InputLabelProps={{
              //   shrink: true,
              // }}
              value={form?.campDate}
              onChange={handleChange}
              onBlur={handleBlur}
             
            />

          </Grid>
         
        
          <Grid item xs={6}>
            <TextField
              id="startTime"
              name="startTime"
              label="Open At"
              type="time"
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              value={form?.startTime}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.startTime !== undefined}
              helperText={errors.startTime}
              className={classes.field}
            />
          </Grid>
          <Grid item xs={6}>
            <TextField
              id="endTime"
              name="endTime"
              label="Close At"
              type="time"
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              value={form?.endTime}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.endTime !== undefined}
              helperText={errors.endTime}
              className={classes.field}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="locality"
              name="locality"
              label="Locality"
              type="text"
              fullWidth
              value={form?.locality}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.locality !== undefined}
              helperText={errors.locality}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="city"
              name="city"
              label="City"
              type="text"
              fullWidth
              value={form?.city}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.city !== undefined}
              helperText={errors.city}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="district"
              name="district"
              label="District"
              type="text"
              fullWidth
              value={form?.district}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.district !== undefined}
              helperText={errors.district}
            />
          </Grid>

         

          <Grid item xs={3}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="zip"
              name="zip"
              label="Pin Code"
              type="text"
              fullWidth
              value={form?.zip}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.zip !== undefined}
              helperText={errors.zip}
            />
          </Grid>
          
        </Grid>


      </DialogContent>
      <DialogActions>
        <Button
          type="submit"
          variant="contained"
          color="secondary"
          onClick={()=>{
            handleSubmit()
            window.location.reload();

          }
           }
        >
          Add
        </Button>
        <Button onClick={() => {
          props.handleClose();
          handleResponse({
            loading: false,
            open: false
          });

        }} color="primary">
          Cancel
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CampForm;
