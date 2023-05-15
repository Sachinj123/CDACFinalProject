import { Grid } from "@material-ui/core";
import PostForm from '../components/post-form';

const PostPage = (props) => {
  return (
    <Grid container spacing={2} justifyContent={"space-around"}>
      <Grid item xs={12} sm={4}>
        <PostForm />
      </Grid>
    </Grid>
  );
};

export default PostPage;
