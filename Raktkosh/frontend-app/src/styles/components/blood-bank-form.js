import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  root: {
  },
  field: {
    margin: theme.spacing(1)
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
}));

export default useStyles;
