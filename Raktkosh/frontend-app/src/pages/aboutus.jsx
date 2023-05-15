import { Grid, Typography, Divider, Paper, makeStyles } from "@material-ui/core";


import PhoneForwardedIcon from '@mui/icons-material/PhoneForwarded';
import InfoIcon from '@mui/icons-material/Info';

const useStyles = makeStyles(theme => ({
  root: {
    maxWidth: "100vw",
    minHeight: "100vh",
    backgroundImage: 'url(https://www.healthline.com/hlcmsresource/images/Blood_Donation-1200x549-facebook.jpg)',
    backgroundRepeat: "no-repeat",
    backgroundSize: "cover",
    padding: "30px",
    display: "flex",
    
    justifyContent: "center",
    flexDirection: "column"
    
  },
  title: {
    fontWeight: "400",
  },
  content: {
    padding: "30px",
    backgroundColor: "#fafafaaa",
    width: "60%",
    marginTop: theme.spacing(2),
    "&:nth-child(even)": {
      marginLeft: "auto"
    }
  }
}));

const AboutUs = (props) => {
  const classes = useStyles();
  return (
    <div className={classes.root}>\
   
    <Typography variant="h2" align="center" className={classes.title}>
       ABOUT US
      </Typography>
  
     
      <br />
      <Divider />
      <br />
      <br />
      <div>
        <Paper className={classes.content}>
          <Typography variant="h3">WHO WE ARE ?</Typography>
          <Typography variant="h5">is an online platform that brings the blood donors and receiver under one roof. It allows the receiver search for the donors in their locality with ease and help them to connect easily without any hassle. It also allows the donors the look up for the receiver or blood bank near them to donate blood.</Typography>
        </Paper>
        <Paper className={classes.content}>
          <Typography variant="h3">BLOOD DONORS</Typography>
          <Typography variant="h5">RAKTKOSH provides users interface to help others by donating blood either directly by responding to seekers or by going to campaigns initiated by Blood Banks.</Typography>
        </Paper>
        <Paper className={classes.content}>
          <Typography variant="h3">BLOOD RECIEPIENTS</Typography>
          <Typography variant="h5">RAKTKOSH provides an interface for users to search blood doners in respective area and to communicate with them for transfusion.</Typography>
        </Paper>
        <Paper className={classes.content}>
          <Typography variant="h3">BLOOD BANKS</Typography>
          <Typography variant="h5">RAKTKOSH provides an interface for blood banks to provide blood to respective seekers and also to search blood doners in respective area by initiating camapigns for blood donation.</Typography>
        </Paper>
      </div>
    </div>
  );
};

export default AboutUs;