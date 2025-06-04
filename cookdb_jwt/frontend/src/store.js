import {createSlice, configureStore, combineReducers} from "@reduxjs/toolkit";
import {persistStore, persistReducer} from "redux-persist";
import storage from "redux-persist/lib/storage";

const userInfoSlice = createSlice({
    name: "userInfo",
    initialState: {
        userInfoList: [],
        adminLoginFlag: false,
        userLoginFlag: false
    },
    reducers: {
        addUserInfo: (state, action) => {
            state.userInfoList.push(action.payload);
        },
        setUerInfoList: (state, action) => {
            state.userInfoList = action.payload;
            state.count = action.payload.length;
        },
        clearUserInfo: (state) => {
            state.userInfoList = [];
        },
        adminLogin: (state) => {
            state.adminLoginFlag = true;
        },
        adminLogout: (state) => {
            state.adminLoginFlag = false;
        },
        userLogin: (state) => {
            state.userLoginFlag = true;
        },
        userLogout: (state) => {
            state.userLoginFlag = false;
        },

    }
});


const initState = {
    token: null,
}

const tokenSlice = createSlice({
    name: "token",
    initialState: initState,
    reducers: {
        setToken: (state, action) => {
            state.token = action.payload;
        }
    }
});

const persistConfig = {
    key: "root",
    storage,
    whitelist: ['userInfo', 'token'], //로컬 스토리지에 저장하기 위해 스토어에 있는 슬라이스 네임을 전부 가져옴
};

const rootReducer = combineReducers({
    userInfo: userInfoSlice.reducer,
    token: tokenSlice.reducer
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
    reducer: persistedReducer,
});

export const persistor = persistStore(store);

export const {
    userLogin,
    userLogout,
    addUserInfo,
    clearUserInfo,
    setUerInfoList,
    adminLogin,
    adminLogout
} = userInfoSlice.actions;
export const {setToken} = tokenSlice.actions;
export default store;