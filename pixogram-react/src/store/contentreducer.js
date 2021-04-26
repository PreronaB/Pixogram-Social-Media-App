import * as actionCreators from '../actions/contentaction'
// 2. create reducer

const initialState = {
    message: '',
    contents: [],
    content: {},
    comment: []
}


const reducer = function (state = initialState, action) {
    switch (action.type) {
        case actionCreators.ADD_CONTENT:
            console.log('add  data from here', action.payload);
            console.log(state.users);
            let newMessage = action.payload.message;
            let newUsers = [...state.users, action.payload.user]
            // newEmployees.push(action.payload)
            return {
                users: newUsers,
                message: newMessage
            }
        case actionCreators.ADD_LIKE:
            console.log('add  data from here', action.payload);
            //console.log(state.contents);
            let newMessages = action.payload.message;
            //let newContentss = [...state.contents, action.payload.contents]
            // newEmployees.push(action.payload)
            return {
              // contents: newContentss,
                message: newMessages
            }
            case actionCreators.ADD_DISLIKE:
            console.log('add  data from here', action.payload);
            //console.log(state.contents);
            let newMessagess = action.payload.message;
            //let newContentsss = [...state.contents, action.payload.contents]
            // newEmployees.push(action.payload)
            return {
               //contents: newContentsss,
                message: newMessagess
            }
            case actionCreators.ADD_COMMENT:
            console.log('add  data from here', action.payload);
            //console.log(state.contents);
            let newComments = action.payload.message;
           //let newContent = [...state.contents, action.payload.contents]
          //  let newComment = [state.comment, action.payload.comment]
            // newEmployees.push(action.payload)
            return {
            //   contents: newContent,
                message: newComments,
               // comment: newComment
            }
        case actionCreators.FETCH_CONTENTS:
            console.log('fetch data from here');
            let freshContents = [...action.payload]
            let users = { ...action.payload.user }
            return {
                contents: freshContents,
                message: state.message,
                user: users
            }
        case actionCreators.DELETE_CONTENT:
            console.log('in reducer delete method');
            // let newMessage1 =  action.payload.text;
            // let freshEmployees = [...action.payload]
            // freshEmployees.filter((employee,i)=> )
            return {
                message: action.payload.text,
                content: action.payload.content
            }
        case actionCreators.FIND_CONTENT:
            console.log('fetch data from here');
            let contentss = [...action.payload]
            console.log(contentss);
            return {
                contents: contentss
                // message: state.message,
                //user:userss,
            }

        default:
            return state;
    }


}

export default reducer;