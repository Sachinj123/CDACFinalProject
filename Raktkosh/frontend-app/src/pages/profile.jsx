import { Backdrop, Card, CardContent, CircularProgress, Container, Divider, Fab, Grid, Snackbar, Typography } from "@material-ui/core";
import { AccountCircleRounded, Email, VerifiedUser, Cake, Phone, InvertColors, LocationCity, Edit } from "@material-ui/icons/";
import { useSelector } from "react-redux";
import Post from '../components/posts';
import ProfilePost from '../components/posts/profilePost'
import useStyles from "../styles/pages/profile";
import useAxios from '../hooks/axios';
import axios from '../config/axios.config';
import { useEffect, useState } from "react";
import { Alert } from "@material-ui/lab";
import PostEditForm from "../components/profile-edit-form";
import AddressEditForm from "../components/user-address-form";
import { useParams } from "react-router";
import { Route, useHistory } from "react-router";

const ProfilePage = props => {
  const history = useHistory();
  const classes = useStyles();

 const id = props.user;
  const userInfo = useSelector(store => store.user);
  const [posts, handlePost] = useState([]);
  const[user, setuser]=useState({});
  const [posterror, handlePostError] = useState({});
  const [open, handleOpen] = useState(false);
  const [openAddress, handleAddressForm] = useState(false);

 const [response, error, waiting] = useAxios(`/account/profile/${id}`);
 
  const [address, errorAdd, waitingAdd] = useAxios(`/user/address/${id}`);




  useEffect(() => {
    if (response) {
      axios.get(`/post/byUser/${response.id}`)
        .then(res => {
          handlePost(res.data);
        })
        .catch(error => {
          if (!error.response) {
            handlePostError(({ open: true, severity: "error", message: "Network Error" }));
          }
          else {
            handlePostError(({ open: true, severity: "error", message: error.response.data.message }));
          }
        });
    }
   
  }, [response]);

  return (
    <Container maxWidth={"lg"}>
      <Backdrop className={classes.backdrop} open={waiting}>
        <CircularProgress color="inherit" />
      </Backdrop>
      <Snackbar open={posterror.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={posterror.severity}>
          {posterror.message}
        </Alert>
      </Snackbar>

      <PostEditForm open={open} handleClose={() => handleOpen(false)} id={userInfo.id} />

      <AddressEditForm open={openAddress} handleClose={() => handleAddressForm(false)} id={userInfo.id} />
      
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Card className={classes.content}>
            <CardContent>
              <Typography className={classes.title} component="h1">
                <VerifiedUser fontSize="large" /> &nbsp; {response?.name}
                <Fab size="small" color="primary" className={classes.edit_btn} onClick={() => handleOpen(!false)}>
                  <Edit />
                </Fab>
              </Typography>
              <Divider />
              <Typography component="h6" className={classes.typo}>
                <AccountCircleRounded fontSize="small" /> &nbsp; {response?.username}
              </Typography>
              {((userInfo.authority === "ADMIN"|| userInfo.authority==="USER")) && (
                <Typography component="h6" className={classes.typo}>
                <Cake fontSize="small" /> &nbsp; {response?.dob}
              </Typography>
          )}
              
              <Typography component="h6" className={classes.typo}>
                <Phone fontSize="small" /> &nbsp; {response?.mobile || "N/A"}
              </Typography>
              <Typography component="h6" className={classes.typo}>
                <Email fontSize="small" /> &nbsp; {response?.email}
              </Typography>
              
              {((userInfo.authority === "ADMIN"|| userInfo.authority==="USER")) && (
                <Typography component="h6" className={classes.typo}>
                <InvertColors fontSize="small" /> &nbsp; {response?.bloodType || "N/A" } {response?.antigen === "POSITIVE" ? "+" : "-"}
              </Typography>
          )}
              
              <Typography component="h6" className={classes.typo}>
                <LocationCity fontSize="small" /> &nbsp; {
                  !address
                  ? "N/A"
                  : `${address?.locality}, ${address?.city} - ${address?.zip} ${address?.state}`
                }
                {(userInfo.id==response?.id)&&(
                  <Fab size="small" color="primary" className={classes.edit_btn} onClick={() => handleAddressForm(!false)}>
                  <Edit />
                </Fab>
                )}
                
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
      

<Grid container spacing={2}>
        {
          
          posts.length !== 0 &&
          posts.map((post, idx) => (
            <Grid item xs={12} sm={6} md={4} className={classes.postContainer} key={idx}>
              <ProfilePost deletable {...post} />
            </Grid>
          ))
        }
      </Grid>
    </Container>
  );
};

export default ProfilePage;