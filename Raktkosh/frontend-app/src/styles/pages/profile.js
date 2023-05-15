import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles(theme => ({
  title: {
    fontSize: 30,
    display: "flex",
    alignItems: "center",
  },
  typo: {
    display: "flex",
    alignItems: "center",
    marginTop: 10
  },
  content: {
    padding: 20,
  },
  postContainer: {
    marginTop: 30
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
  edit_btn: {
    marginLeft: "auto"
  }
}));

export default useStyles;
