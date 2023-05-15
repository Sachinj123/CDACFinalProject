import { Card, CardContent, Container, Fab, Typography, CircularProgress, Link, CardActions } from '@material-ui/core';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from '../config/axios.config';
import useStyles from '../styles/pages/verification';
import clsx from 'clsx';
import CheckIcon from '@material-ui/icons/Check';
import ErrorIcon from '@material-ui/icons/Error';
import WaitIcon from '@material-ui/icons/HourglassEmpty';
import { Link as RouteLink } from "react-router-dom";

const VerificationPage = props => {
  const { token } = useParams();
  const classes = useStyles();
  const [error, handleError] = useState(false);
  const [success, handleSuccess] = useState(false);
  const [loading, handleLoading] = useState(true);

  useEffect(() => {
    handleLoading(true);
    axios.get(`/account/verify/${token}`)
      .then(res => handleSuccess(true))
      .catch(err => handleError(true))
      .finally(() => {
        handleLoading(false);
      });
  }, [token]);

  const buttonClassname = clsx({
    [classes.buttonSuccess]: success,
    [classes.buttonError]: error,
  });

  return (
    <Container maxWidth="md" className={classes.root}>
      <Card variant="outlined" className={classes.card}>
        <CardContent>
          <Typography variant="h3" color="textSecondary">
            {error && "Invalid Token"}
            {loading && "Verifying"}
            {success && "Verified"}
          </Typography>
        </CardContent>
        <div className={classes.loader}>
          <div className={classes.wrapper}>
            <Fab
              color="primary"
              className={buttonClassname}
            >
              {success ? <CheckIcon /> : (error ? <ErrorIcon /> : <WaitIcon />)}
            </Fab>
            {loading && <CircularProgress size={68} className={classes.fabProgress} />}
          </div>
        </div>
        {
          (success || error) && (
            <CardActions>
              <Link component={RouteLink} to="/signin" variant="h5">
                {"Signin"}
              </Link>
            </CardActions>
          )
        }
      </Card>
    </Container>
  );
};
export default VerificationPage;
