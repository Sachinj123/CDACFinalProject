import { Grid } from "@material-ui/core";
import { useEffect } from "react";
import Post from "../components/posts";
import useStyles from "../styles/components/post";
import useAxios from "../hooks/axios";

const HomePage = props => {
  const classes = useStyles();

  const [posts, error, waiting] = useAxios(`/post/`);

  return (
    <Grid container spacing={2}>
      {
        posts &&
        posts.map((post, idx) => (
          <Grid item xs={12} sm={6} md={4} className={classes.postContainer} key={idx}>
            <Post {...post} />
          </Grid>
        ))
      }
    </Grid>
  );
};

export default HomePage;
