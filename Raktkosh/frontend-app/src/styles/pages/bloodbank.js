import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  table: {
    minWidth: 650,
  },
  title: {
    fontSize: 30,
    display: "flex",
    alignItems: "center"
  },
  container: {
    padding: 20
  },
  add_btn: {
    marginLeft: "auto"
  }
}));

export default useStyles;
