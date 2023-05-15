import { Backdrop, Button, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, Grid, InputLabel, MenuItem, Select, Slide, Snackbar, TextField } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import { useState, forwardRef, useEffect } from 'react';
import axios from "../../config/axios.config";
import useStyles from "../../styles/components/blood-bank-form";

const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const AddressEditForm = props => {
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
    if (e.target.value === "" || e.target.value === " ") {
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

  const handleSubmit = () => {
    handleResponse(res => ({ ...res, loading: true }));
    axios.post(`/user/update/address/${props.id}`, form)
      .then(res => {
        handleResponse(res => ({ ...res, severity: "success", message: "Address Updated Successfully" }));
      })
      .catch(err => {
        handleResponse(res => ({ ...res, severity: "error", message: "Failed To Update Address" }));
      })
      .finally(() => {
        handleResponse(res => ({ ...res, open: true, loading: false }));
      });

  };

  useEffect(() => {
    if (props.open) {
      axios.get(`/user/address/${props.id}`)
        .then(res => handleForm({ ...res.data }))
        .catch(err => console.log(err));
    }
  }, [props.open]);

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

      <DialogTitle id="form-dialog-title">Edit Address</DialogTitle>

      <DialogContent>
        <Grid container spacing={1}>
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

          <Grid item xs={9}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              id="state"
              name="state"
              label="State"
              type="text"
              fullWidth
              value={form?.state}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.state !== undefined}
              helperText={errors.state}
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
          Save
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

export default AddressEditForm;
