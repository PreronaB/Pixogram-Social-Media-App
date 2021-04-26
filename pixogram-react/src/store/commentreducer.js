import * as actionCreators from '../actions/commentaction'
// 2. create reducer

const initialState = {
    message: '',
    contents: [],
    comment : {}
}


const reducer = function (state = initialState, action) {
    switch (action.type) {
        // case actionCreators.ADD_COMMENT:
        //     console.log('add  data from here', action.payload);
        //     console.log(state.contents);
        //     let newMessage = action.payload.message;
        //     let newComments = [...state.contents, action.payload.comment]
        //     // newEmployees.push(action.payload)
        //     return {
        //         contents: newComments,
        //         message: newMessage
        //     }
        case actionCreators.DELETE_COMMENT:
            console.log('in reducer delete method');
            // let newMessage1 =  action.payload.text;
            // let freshEmployees = [...action.payload]
            // freshEmployees.filter((employee,i)=> )

            return {
                message: action.payload.text,
                contents: action.payload.contents
            }
        case actionCreators.FIND_COMMENT:
            console.log('fetch data from here');
            let contents = {...action.payload}
            console.log(contents);
            return {
                users: contents
                // message: state.message,
                //user:userss,
                // user : action.payload,
                // setUser : action.payload

            }
        default:
            return state;
    }


}

export default reducer;