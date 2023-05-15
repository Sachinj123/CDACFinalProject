
import {
  Backdrop,
  CircularProgress,
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableBody,
  Typography,
  Divider,
  Fab,
  IconButton,
  Snackbar,
} from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import { Add, Delete } from "@material-ui/icons";
import { Link } from "@material-ui/core";
import { Link as RLink } from "react-router-dom";
import { useAxios } from "../hooks/";
import useStyles from "../styles/pages/bloodbank";
import { useSelector } from "react-redux";
import BloodBankForm from "../components/blood-bank-form";
import { useState } from "react";
import axios from "../config/axios.config";

const BloodBankPage = (props) => {
  const classes = useStyles();
  const userInfo = useSelector((store) => store.user);
  const [banks, error, waiting] = useAxios("/bloodbank/list");
  const [open, handleOpen] = useState(false);
  const [response, handleResponse] = useState({
    loading: false,
    open: false,
  });

  const handleDelete = (id) => {
    handleResponse((res) => ({ ...res, loading: true }));
    axios
      .delete(`/bloodbank/${id}`)
      .then((res) => {
        handleResponse((res) => ({
          ...res,
          severity: "success",
          message: "Blood Bank Deleted Successfully",
          
        }));
      })
      .catch((err) => {
        handleResponse((res) => ({
          ...res,
          severity: "error",
          message: "Failed To Delete Blood Bank",
        }));
      })
      .finally(() => {
        handleResponse((res) => ({ ...res, open: true, loading: false }));
        window.location.reload();
      });
  };

  return (
    <>
      <Backdrop className={classes.backdrop} open={waiting}>
        <CircularProgress color="inherit" />
      </Backdrop>

      <Snackbar
        open={response.open}
        autoHideDuration={6000}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert severity={response.severity}>{response.message}</Alert>
      </Snackbar>

      <BloodBankForm open={open} handleClose={() => handleOpen(false)} />

      <TableContainer component={Paper} className={classes.container}>
        <Typography component="h1" className={classes.title}>
          Registerd Blood Banks
          {(userInfo.authority === "ADMIN") && (
            <Fab
              variant="extended"
              color="primary"
              className={classes.add_btn}
              onClick={() => handleOpen(!false)}
            >
              <Add />
              Add Blood Bank
            </Fab>
          )}
        </Typography>
        <Divider absolute />
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Reg. ID</TableCell>
              <TableCell align="right">Name</TableCell>
              <TableCell align="right">Mobile</TableCell>
              <TableCell align="right">Email</TableCell>
              <TableCell align="right">Open At</TableCell>
              <TableCell align="right">Close At</TableCell>
              {(userInfo.authority === "ADMIN" ) && (
                <TableCell align="right">Delete</TableCell>
              )}
            </TableRow>
          </TableHead>
          <TableBody>
            {banks &&
              banks.map((row) => (
                <TableRow key={row.regID}>
                  <TableCell component="th" scope="row">
                    <Link to={`/bloodbank/${row.id}`} component={RLink}>
                      {row.regID}
                    </Link>
                  </TableCell>
                  <TableCell align="right">{row.name}</TableCell>
                  <TableCell align="right">{row.mobile}</TableCell>
                  <TableCell align="right">{row.email}</TableCell>
                  <TableCell align="right">{row.openAt}</TableCell>
                  <TableCell align="right">{row.closeAt}</TableCell>
                  {(userInfo.authority === "ADMIN" ) && (
                    <TableCell align="center">
                      <IconButton
                        aria-label="delete"
                        color="secondary"
                        onClick={() => handleDelete(row.id)}
                      >
                        <Delete />
                      </IconButton>
                    </TableCell>
                  )}
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default BloodBankPage;
