

import { Card, CardContent, Typography, Divider, Snackbar, Link } from "@material-ui/core";
import { DateRange } from "@material-ui/icons";


import useStyles from "../../styles/components/post";
import React, { useState} from "react";
import { Alert } from "@material-ui/lab";
import { Link as RLink } from 'react-router-dom';


const Post = ({ antigen, createdOn, postCategory, type, userId,state, district,city,locality,zip }) => {
  const classes = useStyles();
 

  const [response, handleResponse] = useState({
    open: false
  });

  
  

  return (
    <>
      <Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>
      <Link component={RLink} to={`/showProfile/${userId.id}`}>
      <Card className={classes.root}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom component="h3">
            {userId.name}
          </Typography>
          <Typography variant="h5" component="h2" className={classes.typo}>
            {postCategory === "DONOR" ? "Available" : "Looking"} for {type}{antigen === "POSITIVE" ? "+" : "-"} blood
          </Typography>
         
            {(state)&&(
              <Typography className={classes.title} color="textSecondary" gutterBottom component="h3">
            {state},{district},{city},{locality},{zip}
          </Typography>
            )}
            
          <Divider />
          <Typography variant="body2" component="p" className={classes.typo}>
            <DateRange fontSize="small" /> &nbsp; {createdOn.replace("T", " at ")}
          </Typography>
        </CardContent>
        
      </Card>
      </Link>
    </>
  );
};

export default Post;