import { Card, CardActions, CardContent, IconButton, Typography, Divider, Snackbar, Link } from "@material-ui/core";
import { DateRange } from "@material-ui/icons";
import axios from '../../config/axios.config';
import DeleteIcon from '@material-ui/icons/DeleteOutline';
import useStyles from "../../styles/components/post";

import { Alert } from "@material-ui/lab";
import { render } from "react-dom";
import React, { useState, useEffect } from 'react';

// import { Link as RLink } from 'react-router-dom';

const ProfilePost = ({ deletable, antigen, createdOn, id, postCategory, type, userId }) => {
  const classes = useStyles();
  
  const [response, handleResponse] = useState({
    open: false
  });
 
  const [isVis, setIsVis] = useState(true);

//  useEffect(()=>{},[])

  const deletePost = (postID) => {
    
    axios.delete(`/post/delete/${postID}`)
      .then(res => {
        handleResponse({ open: true, severity: "success", message: "Post Delete Successfully" });
        // render();
      })
      .catch(err => {
        handleResponse({ open: true, severity: "error", message: "Post Deletion Failed" });
      });
  };

  return (
    <>
      {isVis && (<><Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>
      <Card className={classes.root}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom component="h3">
            {userId.fullname}
          </Typography>
          <Typography variant="h5" component="h2" className={classes.typo}>
            {postCategory === "DONOR" ? "Available" : "Looging"} for {type}{antigen === "POSITIVE" ? "+" : "-"} blood
          </Typography>
          
          <Divider />
          <Typography variant="body2" component="p" className={classes.typo}>
            <DateRange fontSize="small" /> &nbsp; {createdOn.replace("T", " at ")}
          </Typography>
        </CardContent>
        <CardActions>
          {
            deletable && (
              <IconButton aria-label="delete" className={classes.delete} onClick={() => 
                {deletePost(id);
                setInterval(()=>{setIsVis(false)})
              }} >

                <DeleteIcon />
              </IconButton>
            )
          } 
        </CardActions>
      </Card>
      </>
      )}
    </>
  );
};

export default ProfilePost;