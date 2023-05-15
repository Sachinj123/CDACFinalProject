import {
  Dialog, DialogTitle, DialogContent,
  DialogActions, TextField, Button, Slide, Grid, Backdrop, CircularProgress,
  FormControl, MenuItem, Select, Snackbar, InputLabel
} from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import { useState, forwardRef } from 'react';
import axios from "../../config/axios.config";
import useStyles from "../../styles/components/blood-bank-form";


const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const BloodRepositoryForm = props => {
  
  const classes = useStyles();
  const [form, handleForm] = useState({
    type: "",
    antigen: "",
    availability: ""
  });
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

  const validateForm = () => {
    return (Object.keys(form).filter(field => form[field] === "").length === 0 && Object.keys(errors).length === 0);
  };

  const handleSubmit = () => {
    if (validateForm()) {
      handleResponse(res => ({ ...res, loading: true }));
      axios.post(`/bloodrepo/${props.id}`, form)
        .then(res => {
          handleResponse(res => ({ ...res, severity: "success", message: "Blood Repository Added Successfully" }));
        })
        .catch(err => {
          handleResponse(res => ({ ...res, severity: "error", message: "Failed To Add Blood Repository" }));
        })
        .finally(() => {
          handleResponse(res => ({ ...res, open: true, loading: false }));
          window.location.reload();
          
        });
     
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

      <DialogTitle id="form-dialog-title">Add Blood Repository</DialogTitle>
      <DialogContent>
        <Grid container spacing={1}>
          <Grid item xs={6}>
            <FormControl variant="outlined" fullWidth className={classes.formControl}>
              <InputLabel id="blood-type">Blood Type</InputLabel>
              <Select
                labelId="blood-type"
                id="blood-type-select"
                label="Blood Type"
                name="type"
                value={form?.type}
                onChange={handleChange}
                onBlur={handleBlur}
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
          </Grid>
          <Grid item xs={6}>
            <FormControl variant="outlined" fullWidth className={classes.formControl}>
              <InputLabel id="antigen-type">Blood Type</InputLabel>
              <Select
                labelId="antigen-type"
                id="antigen-type-select"
                label="Antigen Type"
                name="antigen"
                value={form?.antigen}
                onChange={handleChange}
                onBlur={handleBlur}
                error={errors?.antigen}
              >
                <MenuItem value=" ">
                  <em>None</em>
                </MenuItem>
                {
                  ["POSITIVE", "NEGATIVE"].map((type, idx) => (
                    <MenuItem value={type} key={type}>{type}</MenuItem>
                  ))
                }
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              id="availability"
              name="availability"
              label="Units Availaible"
              type="availability"
              value={form?.availability}
              onChange={handleChange}
              onBlur={handleBlur}
              error={errors.availability !== undefined}
              helperText={errors.availability}
            />
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          type="submit"
          variant="contained"
          color="secondary"
          // onClick={handleSubmit}
          onClick={() => {
            handleSubmit()
            //
            // props.setRefresh(true);
            //  props.handleRefresh({
            //    setRefresh:true
            //  })
            props.handleClose();
            handleResponse({
              loading: false,
              open: false,

            });
          }}
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

export default BloodRepositoryForm;
