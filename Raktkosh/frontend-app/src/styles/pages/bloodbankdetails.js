import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  root: {
    padding: 20
  },
  typo: {
    display: "flex",
    alignItems: "center",
    marginTop: 10
  },
  title: {
    fontSize: 30,
    display: "flex",
    alignItems: "center"
  },
  container: {
    marginTop: theme.spacing(2)
  },
  table_title: {
    fontSize: 25,
    display: "flex",
    justifyContent: "space-between"
  },
  add_btn: {
    marginLeft: "auto"
  }
}));

export default useStyles;
