import React, { Component } from 'react'
import { connect } from 'react-redux';
import * as actions from '../actions/contentaction'

class FindContent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            contents: []
        }
        this.content_id = React.createRef();

    }

    componentDidMount() {
        // let input = {
        //     user_id: this.user_id.current.value
        // };
        this.props.onFindContents(this.props.user_id);
        //console.log(this.props.username);
    }
    // addLikes() {
    //     let input = {
    //         content_id:this.content_id.current.value,
    //         user_id : this.props.user_id
    //     };
    //     this.props.onAddLikes(input)
    //     // event.preventDefault();
    // }

    //    delete(user_id) {
    //     console.log('delete employee with id: ' + user_id)
    //     this.props.onDeleteUser(this.props.user_id);
    //    }

    // findingUser(event) {
    render() {
        let contentList = this.props.contents
        if (!this.props.contents) {
            return (
                <h1>No Details on this content</h1>
            )
        }
        return (
            <div>
                {/* <div className='form-group'>
                    <div className="mb-3">
                        <label forname="exampleFormControlInput1" className="form-label"><i class="fa fa-id-badge" aria-hidden="true"></i> Content Id</label>
                        <input type="number" name="content_id" ref={this.content_id} className="form-control"  id="exampleFormControlInput1" placeholder="Enter  Id" />
                    </div> */}
                    {/* //         <div className='container'>
        //             <div className='row'>
        //                 <div className='card col-md-8 offset-md-3 offset-md-3'>
        //                     <h3 className='text-center'> POSTS</h3>
        //                     <div className='card-body'>
                                */}
                    {/* <h2 className='text-center'>UserDetail</h2>
                                    <table className='table table-striped table-bordered'>
                                        <thead>
                                            <tr>
                                                <th>POST</th>
                                                <th>CAPTION</th>
                                                <th>LIKES</th>
                                                <th>DISLIKES</th>
                                                {/* <th>STATE</th>
                                                <th>BIO</th> */}
                    {/* <th>ACTIONS</th> */}
                    {/* </tr>
                                        </thead>
                                        <tbody>  */}

                    {contentList.map((content, content_id) => {
                        return (
                            <div className="card col-md-8 offset-md-3 offset-md-3">
                                <img className="card-img-top" src={'http://localhost:8080/uploads/' + content.filename} />
                                <div className='card-body'>
                                    <h3>{content.caption}</h3>
                                    <p>{content.like}</p>
                                    <p>{content.dislike}</p>
                                    {/* <div>
                                        <button type="button" onClick={this.addlikes(this.content.content_id)} className="btn btn-primary">Like</button>
                                    </div> */}
                                </div>
                            </div>
                            // <tr key={content.content_id}>
                            //     <td><img src={'http://localhost:8080/uploads/' + content.filename}/>{content.filename}</td>
                            //     {/* <td>{content.filename}</td> */}
                            //     <td>{content.caption}</td>
                            //     <td>{content.like}</td>
                            //     <td>{content.dislike}</td>
                            //     {/* <td>{this.props.contents.state}</td>
                            //     <td>{this.props.contents.bio}</td> */}
                            //     {/* </td> */}
                            // </tr>
                            // <tr>
                            // </tr>
                        )
                    })}
                    {/* </tbody>
                                    </table> */}

        //                     </div>
        //                 </div>
            //             </div>
            //         </div>
            //     </div>
        );
    }
    //}
}

const mapStateToProps = (state, ownProps) => {
    console.log(state);
    return {
        // message: state.message,
        contents: state.contentreducer.contents,
        user_id: localStorage.getItem('user_id')

    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onFindContents: (user_id) => {
            console.log('debug info')
            return dispatch(actions.findContents(user_id));
        },
        
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(FindContent);

