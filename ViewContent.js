import React, { Component } from 'react'
import { connect } from 'react-redux';
import Badge from '../Badge2'
import * as actions from '../actions/contentaction'
import {
    Link
} from "react-router-dom";
class ViewContent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            contents: []
        }
        this.comment= React.createRef();
    }

    componentDidMount() {
        // let input = {
        //     user_id: this.user_id.current.value
        // };
        this.props.onFetchContents();
        //console.log(this.props.username);
    }

    //    delete(user_id) {
    //     console.log('delete employee with id: ' + user_id)
    //     this.props.onDeleteUser(this.props.user_id);
    //    }
    addLikes(id) {
        // let input = {
        //     content_id:this.props.contents.content_id,
        //     user_id : this.props.user_id
        // };
        console.log(this)
        console.log('delete employee with id: ' + id)
        this.props.onAddLikes(id)
        // event.preventDefault();
    }
    addDislikes(id) {
        // let input = {
        //     content_id:this.props.contents.content_id,
        //     user_id : this.props.user_id
        // };
        console.log(this)
        console.log('delete employee with id: ' + id)
        this.props.onAddDislikes(id)
        // event.preventDefault();
    }
    addComments(id,comment) {
        let input = {
            comment:this.comment.current.value
        };
        console.log(this.comment.current.value)
        console.log('delete employee with id: ' + id)
        this.props.onAddComments(id,this.comment.current.value)
        //event.preventDefault();
    }

    render() {
        console.log(this)
        console.log(this.props)
        // commentList= this.props.contents.comments
        if (!this.props.contents) {
            return (
                <h1>No Contents on this user</h1>
            )
        }
        let contentList = this.props.contents.map((content, content_id) => {
            if(content.filetype ==='image/png' || 'image/jpeg'){
            return (
                <div className="card col-md-8 offset-md-3 offset-md-3">
                    <img className="card-img-top" src={'http://localhost:8080/uploads/' + content.filename} width='30px' height='30' />
                    <div className='card-body'>
                        <h3>{content.caption}</h3>
                       {/* <div> {commentList.map(comment,comment_id)=>{
                            return(
                                <div>
                                {this.props.contents.comments}
                                </div>
                            )
                        }} */}
                        <p>{content.comments}</p>
                        {/* <p>{content.like}</p>
                        <p>{content.dislike}</p> */}
                        <div className="form-group">
                            {/* <label forname="exampleInputEmail1"><i class="fa fa-envelope"></i>Comment</label> */}
                            <input type="text" name='comment' ref={this.comment} className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter Comment" />
                        </div>
                        <button type="button" onClick={this.addComments.bind(this, content.content_id,this.comment)} className="btn btn-primary"><i class="fa fa-comment" aria-hidden="true"></i></button>
                        {/* <Badge caption="Likes" count={content.like} onClick={this.addLikes.bind(this, content.content_id)}/> */}
                        <button type="button" onClick={this.addLikes.bind(this, content.content_id)} className="btn btn-primary"><i class="fa fa-thumbs-up" aria-hidden="true"></i> {content.like}</button>
                        <button type="button" onClick={this.addDislikes.bind(this, content.content_id)} className="btn btn-primary"><i class="fa fa-thumbs-down" aria-hidden="true"></i> {content.dislike}</button>

                    </div>
                </div>
            
                // <tr key={content_id}>
                //     {/* <td>{user.user_id}</td> */}
                //     {/* <td><Link to={'/users/' + user.user_id}>{user.username}</Link></td> */}
                //     <td>{content.content_id}</td>
                //     <td><img src={'http://localhost:8080/uploads/' + content.filename}/>{content.filename}</td>
                //     {/* <td>{content.filename}</td> */}
                //     <td>{content.filetype}</td>
                //     <td>{content.caption}</td>
                //     {/* <td>{content.user.user_id}</td> */}
                //     <td>{content.like}</td>
                //     <td>{content.dislike}</td>
                //     {/* <td>{user.bio}</td> */}
                //     {/* <td><button className='mr-2' onClick={this.delete.bind(this, user.user_id)} type="button" className="btn btn-danger"><i class="fa fa-trash" aria-hidden="true"></i></button>
                //      <button className ='mr-5'onClick={this.updateUser.bind(this)} type="button" className="btn btn-dark"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button>
                //     </td> */}
                // </tr>

            )}
            else{
                return(
                  <div>
                      <div className="card col-md-8 offset-md-3 offset-md-3">
                    <video className="card-img-top" src={'http://localhost:8080/uploads/' + content.filename} width='30px' height='30' />
                    <div className='card-body'>
                        <h3>{content.caption}</h3>
                       {/* <div> {commentList.map(comment,comment_id)=>{
                            return(
                                <div>
                                {this.props.contents.comments}
                                </div>
                            )
                        }} */}
                        <p>{content.comments}</p>
                        {/* <p>{content.like}</p>
                        <p>{content.dislike}</p> */}
                        <div className="form-group">
                            {/* <label forname="exampleInputEmail1"><i class="fa fa-envelope"></i>Comment</label> */}
                            <input type="text" name='comment' ref={this.comment} className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter Comment" />
                        </div>
                        <button type="button" onClick={this.addComments.bind(this, content.content_id,this.comment)} className="btn btn-primary">Comment</button>
                        {/* <Badge caption="Likes" count={content.like} onClick={this.addLikes.bind(this, content.content_id)}/> */}
                        {/* <button type="button" onClick={this.addLikes.bind(this, content.content_id)} className="btn btn-primary">Like</button> */}
                        <button type="button" onClick={this.addDislikes.bind(this, content.content_id)} className="btn btn-primary">Dislike</button>

                    </div>
                    </div> 
                    </div>  

                )
            }
        })

    
        return (
            <div>
                {contentList}
            </div>
            //     {/* <div className="mb-3">
            //     <div className={(this.props.message === '') ? '' : 'alert alert-success'} role="alert">
            //         {this.props.message}
            //     </div>
            // </div> */}
            //     <h2 className='text-center'>Content List</h2>
            //     <table className='table table-striped table-bordered'>
            //         <thead>
            //             <tr>
            //                 <th>CONTENTID</th>
            //                 <th>FILENAME</th>
            //                 <th>FILETYPE</th>
            //                 <th>CAPTION</th>
            //                 {/* <th>USERID</th> */}
            //                 <th>LIKES</th>
            //                 <th>DISLIKES</th>
            //                 {/* <th>ACTIONS</th> */}
            //             </tr>
            //         </thead>
            //         <tbody>
            //            
            //         </tbody>

            //     </table>
            // </div>
            // {/* <div className="nav justify-content-start">
            //     {/* <button className ='mr-5'onClick={() => history.push('/Products')} type="button" className="btn btn-dark">Update</button> */}
            //     <Link className="nav-link " to="/userupdate">UPDATE USER</Link>
            // </div> */}

        )
    }


}

const mapStateToProps = (state, ownProps) => {
    console.log(state);
    return {
        // message: state.message,
        contents: state.contentreducer.contents,
        user: state.contentreducer.user,
        user_id: localStorage.getItem('user_id')

    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onFetchContents: () => {
            console.log('debug info')
            return dispatch(actions.fetchContents());
        },
        onAddLikes: (id) => dispatch(actions.addLike(id)),
        onAddDislikes: (id) => dispatch(actions.addDislike(id)),
        onAddComments: (id,comment) => dispatch(actions.addComment(id,comment))
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(ViewContent);

