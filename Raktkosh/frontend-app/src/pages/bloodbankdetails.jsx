import {
  Container,
  Divider,
  Paper,
  Typography,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableBody,
  Backdrop,
  CircularProgress,
  IconButton,
  Fab,
  Snackbar,
} from "@material-ui/core";
import {
  TimerSharp,
  Email,
  Phone,
  VerifiedUser,
  LocationCity,
  Delete,
  Add,
} from "@material-ui/icons";
import { useEffect, useState ,useReducer} from "react";
import { useHistory, useParams, useLocation } from "react-router";
import { useAxios } from "../hooks";
import axios from "../config/axios.config";
import useStyles from "../styles/pages/bloodbankdetails";
import { useSelector } from "react-redux";
import BankRepositoryForm from "../components/blood-repository-form";
import { Alert } from "@material-ui/lab";

const BloodBankDetails = (props) => {
  const id = useParams().id;
  const history = useHistory();
  const location = useLocation();
  const [data, error, waiting] = useAxios(`/bloodbank/${id}`);
  const [bb_address, errors, waitings] = useAxios(`/user/bloodbank/address/${id}`);
  const [repo, handleRepo] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const [open, handleOpen] = useState(false);
  const classes = useStyles();
  const [ignored, forceUpdate] = useReducer(x => x + 1, 0);
  
  const [response, handleResponse] = useState({
    loading: false,
    open: false,
  });

  const userInfo = useSelector((store) => store.user);

  useEffect(() => {
    if (data) {
      axios
        .get(`/bloodrepo/${data.id}`)
        .then((res) => handleRepo(res.data))
        .catch((err) => console.log(err));
    }
  }, [data,ignored]);

  const handleDelete = (repoID) => {
    axios
      .delete(`/bloodrepo/${id}`, { data: repoID })
      .then((res) => {
        handleResponse((res) => ({
          ...res,
          severity: "success",
          message: "Blood Repository Deleted Successfully",
        }));
        
      })
      .catch((err) => {
        handleResponse((res) => ({
          ...res,
          severity: "error",
          message: "Failed To Delete Blood Repository",
        }));
      })
      .finally(() => {

        handleResponse((res) => ({ ...res, open: true, loading: false }));
        window.location.reload();
      });
  };

  return (
    <Container maxWidth={"lg"}>
      <Backdrop className={classes.backdrop} open={waiting || response.open}>
        <CircularProgress color="inherit" />
      </Backdrop>

      {data && (
        <BankRepositoryForm refresh={setRefresh}
          open={open}
          id={data.id}
          handleClose={() => {
            handleOpen(false);
            setRefresh(true);
            
          }}
          
        />
      )}

      <Snackbar
        open={response.open}
        autoHideDuration={6000}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert severity={response.severity}>{response.message}</Alert>
      </Snackbar>

      {data && (
        <Paper variant="outlined" className={classes.root}>
          <Typography component="h1" className={classes.title}>
            <VerifiedUser fontSize="large" /> &nbsp; {data.name}
            {(userInfo.authority === "ADMIN" || userInfo.id===data.userId) && (
              <Fab
                size="small"
                color="primary"
                className={classes.add_btn}
                onClick={() => handleOpen(!false)}
              >
                <Add />
              </Fab>
            )}
          </Typography>
          <Divider />
          <Typography component="h6" className={classes.typo}>
            <Phone fontSize="small" /> &nbsp; {data?.mobile || "N/A"}
          </Typography>
          <Typography component="h6" className={classes.typo}>
            <Email fontSize="small" /> &nbsp; {data.email}
          </Typography>
          <Typography component="h6" className={classes.typo}>
            <TimerSharp fontSize="small" /> &nbsp; Operate from {data.openAt} AM
            to {data.closeAt} PM
          </Typography>
          
          <Typography component="h6" className={classes.typo}>
            <LocationCity fontSize="small" /> &nbsp;{" "}
            {!bb_address?.state
              ? "N/A"
              : `${bb_address.locality}, ${bb_address.city} - ${bb_address.zip} ${bb_address.state}`}
          </Typography>
          {repo.length !== 0 && (
            <TableContainer className={classes.container}>
              <Typography component="h1" className={classes.table_title}>
                Repository
              </Typography>
              <Table className={classes.table} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center">Blood Group</TableCell>
                    <TableCell align="center">Antigen</TableCell>
                    <TableCell align="center">Units Available</TableCell>
                    {(userInfo.authority === "ADMIN" || userInfo.id===data.userId)&& (
                    <TableCell align="center">Delete</TableCell>)}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {repo.map((row, idx) => (
                    <TableRow key={idx}>
                      <TableCell align="center">{row.id.type}</TableCell>
                      <TableCell align="center">{row.id.antigen}</TableCell>
                      <TableCell align="center">{row.availability}</TableCell>


                      {(userInfo.authority === "ADMIN" || userInfo.id===data.userId) &&  (
                       <TableCell align="center">

                          <IconButton aria-label="delete" color="secondary" onClick={() => handleDelete({
                            type: row.id.type,
                            antigen: row.id.antigen
                          })}>
                            <Delete />
                          </IconButton>

                        </TableCell>)}

                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          )}
        </Paper>
      )}
    </Container>
  );
};

export default BloodBankDetails;
