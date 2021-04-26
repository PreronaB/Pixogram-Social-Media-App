import * as actionCreators from '../actions/followeraction'
// 2. create reducer

const initialState = {
    message: '',
    followers: [],
    follower:{}
}

const reducer = function (state = initialState, action) {
    switch (action.type) {
        case actionCreators.ADD_FOLLOWER:
            console.log('add  data from here', action.payload);
            let newMessage = action.payload.message;
            let newFollowers = [state.followers, action.payload.follower]
            // newEmployees.push(action.payload)
            return {
                followers: newFollowers,
                message: newMessage
            }
        case actionCreators.DELETE_FOLLOWER:
            console.log('in reducer delete method');
            // let newMessage1 =  action.payload.text;
            // let freshEmployees = [...action.payload]
            // freshEmployees.filter((employee,i)=> )
            return {
                message: action.payload.text,
                followers: action.payload.followers
            }
        default:
            return state;
    }
}

export default reducer;