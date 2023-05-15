import { Dialog, DialogContent, DialogTitle, Slide, Typography } from "@material-ui/core";
import { forwardRef } from "react";
import { makeStyles } from "@material-ui/core";
import { mergeClasses } from "@material-ui/styles";
import { red } from "@material-ui/core/colors";

const useStyles = makeStyles(theme => ({
  root: {
    padding: "50px"
  },
  title: {
    textAlign: "center",
    color: red[500],
  }
}));

const Transition = forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const AccountNotEnableDialog = props => {
  const classes = useStyles();
  return (
    <Dialog open={props.open} onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
      TransitionComponent={Transition}
      className={classes.root}
    >
      <DialogTitle id="form-dialog-title">
        <Typography className={classes.title}  variant="h3">Account is not enabled</Typography>
      </DialogTitle>
      <DialogContent>
        <Typography variant="h4">
          Check your email, and click on the link provied to activate your account.
        </Typography>
      </DialogContent>
    </Dialog>
  );
};

export default AccountNotEnableDialog;
