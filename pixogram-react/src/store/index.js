import { combineReducers } from "redux"

import userreducer from "./reducer"
import followerreducer from "./followerreducer"
import commentreducer from "./commentreducer"
import contentreducer from "./contentreducer"

export default combineReducers({
     followerreducer,
     userreducer,
     commentreducer,
     contentreducer

})