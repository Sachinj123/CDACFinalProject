import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  root: {
    minWidth: 375,
  },
  title: {
    fontSize: 24,
  },
  pos: {
    marginBottom: 12,
  },
  formControl: {
    marginTop: theme.spacing(2),
  },
  submit: {
    marginTop: theme.spacing(2)
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
}));

export default useStyles;
