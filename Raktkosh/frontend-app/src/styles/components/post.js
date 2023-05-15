import { makeStyles } from "@material-ui/core";
import { red } from "@material-ui/core/colors";

const useStyles = makeStyles({
  root: {
    minWidth: 275,
    padding: 20,
    marginBottom: 10,
    display: "flex",
    justifyContent: "space-between",
    alignItems: "flex-start"
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)',
  },
  title: {
    fontSize: 14,
  },
  pos: {
    marginBottom: 12,
  },
  delete: {
    color: red[300],
    fontSize: 20
  },
  typo: {
    display: "flex",
    alignItems: "center",
    marginTop: 10
  },
});

export default useStyles;
