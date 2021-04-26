
import axios from 'axios';

export const ADD_COMMENT = 'ADD_COMMENT'
export const DELETE_COMMENT = 'DELETE_COMMENT'
export const FIND_COMMENT = 'FIND_COMMENT'



const addUrl = 'http://localhost:8080/api/comment'
const delUrl = 'http://localhost:8080/api/commentdel/'
const findUrl ='http://localhost:8080/api/comment/'



const saveComment = (payload) => {
    return {
        type: ADD_COMMENT,
        payload
    };
}

export const addComment = (payload) => {

    return dispatch => {
        
        axios.post(addUrl, payload)
          .then((response) => {
            alert(response.data.text);
            console.log(response.data);
            dispatch(saveComment(response.data))
          })
          .catch(function (error) {
            console.log(error);
          //  dispatch(saveComment(response.data))
          });
    }

}



const removeComment = (payload) => {
    return {
        type: DELETE_COMMENT,
        payload
    };
}


export const deleteComment = (comment_id) => {
    return dispatch => {
        // fetch(baseUrl)
        console.log('axios delete...')
        axios.delete(delUrl + comment_id)
            // .then(res => res.json())
            .then(res =>{
                console.log('After http response',  res.data)
                dispatch(removeComment(res.data))
            }  );
    }
}

const findComment = (payload) => {
    return {
        type: FIND_COMMENT,
        payload

    };
}

export const findComments= (comment_id) => {
    return dispatch => {
        //fetch(getUrl)
        axios(findUrl + comment_id)
            //.then(res => res.json())
            .then(res => {
                console.log(res.data);
                dispatch(findComment(res.data))
            });
    }
}