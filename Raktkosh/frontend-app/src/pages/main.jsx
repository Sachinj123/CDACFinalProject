import React, { useEffect } from 'react';
import { Route, useHistory } from "react-router";
import clsx from 'clsx';
import {
  useTheme, Drawer, AppBar, Toolbar, List, CssBaseline, Typography,
  Divider, IconButton, ListItem, ListItemText, ListItemIcon
} from '@material-ui/core';
import {
  AccountCircle, ExitToApp, ChevronLeft, ChevronRight, LocalHospital, LocalHospitalOutlined,
  WbSunny, Menu, Home, PostAdd, Brightness2, SupervisedUserCircleOutlined
} from '@material-ui/icons';
import CampaignIcon from '@mui/icons-material/Campaign';

import useStyles from '../styles/pages/main';
import { useDispatch, useSelector } from "react-redux";
import { switchTheme } from '../redux/actions';
import axios from '../config/axios.config';
import HomePage from './home';
import PostPage from './post';
import { title } from '../application.json';
import Copyright from '../components/copyright';
import ProfilePage from './profile';
import { setUserDetails } from '../redux/actions';
import BloodBankPage from './bloodbank';
import BloodBankDetails from './bloodbankdetails';
import AccountNotEnableDialog from '../components/account-enable-dialog';
import ShowProfile from './showProfile';
import CampPostTable from './camppost';
import GroupsIcon from '@mui/icons-material/Groups';
import InfoIcon from '@mui/icons-material/Info';
import ContactUs from './contactus';
import AboutUs from './aboutus';

const MainPage = () => {
  const classes = useStyles();
  const theme = useTheme();
  const userInfo = useSelector(store => store.user);
  const dispatch = useDispatch();
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };
  const pages = [
    { pagename: 'Home', link: "/home", component: <Home /> },
    { pagename: 'Profile', link: `/profile/`, component: <AccountCircle /> },
    { pagename: 'Post', link: "/post", component: <PostAdd /> },
    { pagename: 'Blood Bank', link: "/bloodbank", component: <LocalHospital /> },
    { pagename: 'Add Camp', link: "/camp", component: <CampaignIcon /> },
    { pagename: 'About Us', link: "/about", component: <InfoIcon /> },
    { pagename: 'Contact Us', link: "/contact", component: <GroupsIcon /> }
  ]

  const filterpage = pages.filter(
    function (page) {
      if (userInfo.authority === "BLOOD_BANK" || userInfo.authority === "CAMP") {
        if (page.pagename === "Post") {
          return null;
        }
      }
      return page;
    }
  )

  const history = useHistory();

  const logout = () => {
    localStorage.setItem("token", "");
    history.push("/signin");
  };

  useEffect(() => {
    if (!localStorage.getItem("token")) {
      history.push("/signin");
    }
    else {
      if (Object.keys(userInfo).length === 0) {
        axios.get("/account/info/" + localStorage.getItem("token"))
          .then(res => res.data)
          .then(data => {
            let { name, email, username, id, enabled, authorities: [...authority] } = data;
            authority = authority[0].authority;
            dispatch(setUserDetails({ name, email, username, id, enabled, authority }));
            return data;
          })
          .catch(err => console.log(err));
      }
    }
  }, [history, dispatch, userInfo]);


  return (
    <div className={classes.root}>
      <CssBaseline />

      <AccountNotEnableDialog open={!userInfo.enabled} />

      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, {
              [classes.hide]: open,
            })}
          >
            <Menu />
          </IconButton>
          <Typography variant="h4" className={classes.title}>
            {title}
          </Typography>
          <IconButton
            aria-label="account of current user"
            aria-controls="primary-search-account-menu"
            aria-haspopup="true"
            color="inherit"
            onClick={() => dispatch(switchTheme())}
          >
            {
              useSelector(store => store.theme)
                ? <Brightness2 />
                : <WbSunny />
            }
          </IconButton>
          <IconButton
            aria-label="account of current user"
            aria-controls="primary-search-account-menu"
            aria-haspopup="true"
            color="inherit"
            onClick={logout}
          >
            <ExitToApp />
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        className={clsx(classes.drawer, {
          [classes.drawerOpen]: open,
          [classes.drawerClose]: !open,
        })}
        classes={{
          paper: clsx({
            [classes.drawerOpen]: open,
            [classes.drawerClose]: !open,
          }),
        }}
      >
        <div className={classes.toolbar}>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'rtl' ? <ChevronRight /> : <ChevronLeft />}
          </IconButton>
        </div>
        <Divider />
        <List>
          {filterpage.map((entry, index) => (
            <ListItem onClick={() => history.push(entry.link)} key={index} className={classes.links}>
              <ListItemIcon>{entry.component}</ListItemIcon>
              <ListItemText primary={entry.pagename} />
            </ListItem>
          ))}
        </List>
        <Divider />
      </Drawer>
      <main className={classes.content}>
        <div className={classes.toolbar} />
        <Route path="/home">
          <HomePage />
        </Route>
        <Route path="/profile/">
          <ProfilePage user={userInfo.id} />
        </Route>
        <Route path="/post">
          <PostPage />
        </Route>
        <Route exact path="/bloodbank">
          <BloodBankPage />
        </Route>
        <Route path="/bloodbank/:id">
          <BloodBankDetails />
        </Route>
        <Route path="/showProfile/:id">
          <ShowProfile />
        </Route>
        <Route path="/camp">
          <CampPostTable />
        </Route>
        <Route path="/contact">
          <ContactUs />
        </Route>
        <Route path="/about">
          <AboutUs />
        </Route>

        <Copyright className={classes.copyright} />
      </main>
    </div>
  );
}

export default MainPage;
