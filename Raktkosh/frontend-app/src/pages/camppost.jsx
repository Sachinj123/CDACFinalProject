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
import { useAxios } from "../hooks/";
import useStyles from "../styles/pages/bloodbank";
import { useSelector } from "react-redux";
import BloodBankForm from "../components/blood-bank-form";
import { useState } from "react";
import axios from "../config/axios.config";
import CampForm from "../components/camp-form";

const CampPostTable = (props) => {
  const classes = useStyles();
  const userInfo = useSelector((store) => store.user);
  const [camps, error, waiting] = useAxios("/camp/");
  const [open, handleOpen] = useState(false);
  const [response, handleResponse] = useState({
    loading: false,
    open: false,
  });

  const handleDelete = (id) => {
    handleResponse((res) => ({ ...res, loading: true }));
    axios
      .delete(`/camp/delete/${id}`)
      .then((res) => {
        handleResponse((res) => ({
          ...res,
          severity: "success",
          message: "Blood Donation Camp Deleted Successfully",
        }));
      })
      .catch((err) => {
        handleResponse((res) => ({
          ...res,
          severity: "error",
          message: "Failed To Delete Camp Details",
        }));
      })
      .finally(() => {
        handleResponse((res) => ({ ...res, open: true, loading: false }));
        window.location.reload()
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

      <CampForm open={open} handleClose={() => handleOpen(false)} />

      <TableContainer component={Paper} className={classes.container}>
        <Typography component="h1" className={classes.title}>
          Upcoming Donation Camps
          {(userInfo.authority === "ADMIN") && (
            <Fab
              variant="extended"
              color="primary"
              className={classes.add_btn}
              onClick={() => handleOpen(!false)}
            >
              <Add />
             Add Blood Donation Camp
            </Fab>
          )}
        </Typography>
        <Divider absolute />
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              {/* <TableCell>Reg. ID</TableCell> */}
              <TableCell align="center">Name</TableCell>
              <TableCell align="center">Date</TableCell>
              <TableCell align="center">Locality</TableCell>
              <TableCell align="center">City</TableCell>
              <TableCell align="center">District</TableCell>
              <TableCell align="center">Zipcode</TableCell>
              <TableCell align="center">Open At</TableCell>
              <TableCell align="center">Close At</TableCell>
              {(userInfo.authority === "ADMIN") && (
                <TableCell align="right">Delete</TableCell>
              )}
            </TableRow>
          </TableHead>
          <TableBody>
            {camps &&
              camps.map((row) => (
                <TableRow key={row.id}>
                  {/* <TableCell component="th" scope="row">
                    <Link to={`/camp/${row.id}`} component={RLink}>
                      {row.regID}
                    </Link>
                  </TableCell> */}
                  <TableCell align="center">{row.name}</TableCell>
                  <TableCell align="center">{row.campDate}</TableCell>
                  <TableCell align="center">{row.locality}</TableCell>
                  <TableCell align="center">{row.city}</TableCell>
                  <TableCell align="center">{row.district}</TableCell>
                  <TableCell align="center">{row.zip}</TableCell>
                  <TableCell align="center">{row.startTime}</TableCell>
                  <TableCell align="center">{row.endTime}</TableCell>
                  {(userInfo.authority === "ADMIN"
        ) && (
                    <TableCell align="right">
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

export default CampPostTable;
