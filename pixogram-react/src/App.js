import logo from './logo.svg';
import './App.css';
import Badge from './Badge2'
import Card from './components/Card'
import Hello from './components/Hello';
import Para from './components/Para';
import CondIf from './components/CondIf';
import List from './components/List';
import User from './components/User.jsx'
import UserForm from './components/UserForm.jsx'
import Dropdown from './components/Dropdown'
import AddUser from './components/AddUser';
import Users from './components/Users';
import Header from './components/Header.js'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import UpdateUser from './components/UpdateUser';
// import LoginUser from './components/LoginUser';
import Home from './components/Home.js';
import Error from './components/Error.js';
import FindUserById from './components/FindUserById';
import UploadFile from './components/UploadFile';
import AddFollower from './components/AddFollower';
import DeleteFollower from './components/DeleteFollower';
import UploadFiles from './components/UploadFiles';
import ViewContent from './components/ViewContent';
import Login from './components/Login';
import Footer from './components/Footer';
import UserAdd from './components/UserAdd';
import UserUpdate from './components/UserUpdate';
import FindContent from './components/FindContent';
import FindUser from './components/FindUser';
import { Container, Row, Col } from 'react-bootstrap';
import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import FollowerAdd from './components/FollowerAdd';
import FollowerDelete from './components/FollowerDelete';

function App() {
  const MarginTop = {
    marginTop: "20px"
  };

  return (
    <div className="App">
      <Router>
        <NavigationBar/>
        <Container>
          <Row>
            <Col></Col>
            <Col></Col>
            <Col></Col>
            <Col></Col>
          </Row>
          <Row>
            <Col>
            </Col>
            <Col lg={12} style={MarginTop}>
              <Switch>
                <Route exact path="/" component={Login}>
                </Route>
                <Route exact path="/welcome" component={Home} />
                <Route exact path="/home" component={Welcome}>
                </Route>
                <Route path="/useradd" component={UserAdd}>
                </Route>
                <Route path="/userall" component={Users}>
                </Route>
                <Route path="/editprofile" component={UserUpdate}>
                </Route>
                <Route exact path="/users/:user_id" component={FindUserById}>
                </Route>
                <Route exact path="/post" component={UploadFiles}>
                </Route>
                <Route exact path="/follow" component={FollowerAdd}>
                </Route>
                <Route exact path="/unfollow" component={FollowerDelete}>
                </Route>
                <Route exact path="/uploads" component={ViewContent}>
                </Route>
                <Route exact path="/profile/:user_id" component={FindContent}>
                </Route>
                <Route exact path="/error" component={Error}>
                </Route>
              </Switch>
              <Col></Col>
              <Col></Col>
            </Col>
          </Row>
        </Container>
        <Footer />
      </Router>
    </div>
  );
}
export default App;


