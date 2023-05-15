import { Card, CardActions, CardContent, IconButton, Typography, Divider, Snackbar, Link } from "@material-ui/core";
import { DateRange } from "@material-ui/icons";
//import axios from '../../config/axios.config';

import useStyles from "../../styles/components/post";
import React, { useState} from "react";
import { Alert } from "@material-ui/lab";

const Camp=({name,campDate,city,locality,district,zipcode,startTime,endTime})=>{
    const classes = useStyles();
    return(
        <>
         <Snackbar open={response.open} autoHideDuration={6000} anchorOrigin={{ vertical: 'top', horizontal: 'right' }}>
        <Alert severity={response.severity}>
          {response.message}
        </Alert>
      </Snackbar>
     
      <Card className={classes.root}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom component="h3">
            {userId.name}
          </Typography>
          <Typography variant="h5" component="h2" className={classes.typo}>
            {postCategory === "DONOR" ? "Available" : "Logging"} for {type}{antigen === "POSITIVE" ? "+" : "-"} blood
          </Typography>
          
            <Typography className={classes.title} color="textSecondary" gutterBottom component="h3">
            {state},{district},{city},{locality},{zip}
          </Typography>
          <Divider />
          <Typography variant="body2" component="p" className={classes.typo}>
            <DateRange fontSize="small" /> &nbsp; {createdOn.replace("T", " at ")}
          </Typography>
        </CardContent>
        
      </Card>
      
        </>
    )

}
export default Camp;