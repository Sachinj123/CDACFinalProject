import { Link, Typography } from '@material-ui/core';
import { title, url } from '../../application.json';

const Copyright = (props) => {
  return (
    <Typography variant="body2" color="textSecondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href={url}>
      { title }
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
};

export default Copyright;
