import React, { Component } from 'react'
import { connect } from 'react-redux';
import * as actions from '../actions/action'
import { Row, Col, Container, ListGroup, Button, Image } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';

class FindUserById extends Component {
    constructor(props) {
        super(props)

        this.state = {
            users: []
        }
        // this.user_id=React.createRef();
    }

    componentDidMount() {
        // let input = {
        //     user_id: this.user_id.current.value
        // };
        this.props.onFindUsers(this.props.user_id);
        //console.log(this.props.username);
    }

    followUser() {
        this.props.history.push('/follow')
    }
    unfollowUser() {
        this.props.history.push('/unfollow')
    }

    // findingUser(event) {
    render() {
        if (!this.props.users) {
            return (
                <h1>No Details on this user</h1>
            )
        }
        return (
            <div>
                <Container>
                <Row>
                <Row>
                    <Col>
                    <Image src="/about.gif" width='400px'  height='500px'/>
                    </Col>
                    <Col>
                <Card style={{ width: '20rem' }}>
                    {/* <Card.Img variant="top" src="slide2.png"  /> */}
                    <Card.Body>
                        <Card.Title style={{ align: 'center' }}><h2><b>PROFILE</b></h2></Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                   <b>USER ID:</b>  {this.props.users.user_id}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                <b>USER NAME:</b>  {this.props.users.username}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                <b>EMAIL:</b> {this.props.users.email}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                <b>STATE:</b>  {this.props.users.state}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                <b> GENDER:</b>  {this.props.users.gender}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                <b> BIO:</b>  {this.props.users.bio}
                                </ListGroup.Item>
                            </ListGroup>
                        <Button variant="primary">Follow</Button>
                        <Button variant="secondary">Unfollow</Button>
                    </Card.Body>
                </Card>
                </Col>
                </Row>
                </Row>
                </Container>
            </div>

        );
    }
    //}
}

const mapStateToProps = (state, ownProps) => {
    console.log(state);
    return {
        // message: state.message,
        users: state.userreducer.users,
        user_id: ownProps.match.params.user_id,
        history: ownProps.history

    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onFindUsers: (user_id) => {
            console.log('debug info')
            return dispatch(actions.findUsers(user_id));
        },
        // onDeleteUser: (user_id) => dispatch(actions.deleteUser(user_id))
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(FindUserById);

