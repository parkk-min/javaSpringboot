import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import MainLayout from "./pages/MainLayout";
import Home from "./pages/Home";
import DetailCondition from "./pages/DetailCondition";
import ConditionSelect from "./pages/ConditionSelect";
import DisplayUserInfo from "./pages/DisplayUserInfo";
import DisplayBuyInfo from "./pages/DisplayBuyInfo";
import CreateUserInfo from "./pages/CreateUserInfo";
import JoinUser from "./pages/JoinUser";
import AddBuyInfo from "./pages/AddBuyInfo";
import JoinUserResult from "./pages/JoinUserResult";
import AddBuyInfoResult from "./pages/AddBuyInfoResult";
import AdminLogin from "./pages/AdminLogin";
import AdminJoin from "./pages/AdminJoin";
import Logout from "./pages/Logout";
import UserLogin from "./pages/UserLogin";


function App() {

    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainLayout/>}>
            <Route index element={<Home/>}/>
            <Route path="/user-login" element={<UserLogin/>}></Route>
            <Route path="/admin-login" element={<AdminLogin/>}></Route>
            <Route path="/admin-join" element={<AdminJoin/>}></Route>
            <Route path="/search" element={<ConditionSelect/>}>
              <Route path="/search/detail-condition" element={<DetailCondition/>}/>
            </Route>
            <Route path="/display-userinfo" element={<DisplayUserInfo/>}></Route>
            <Route path="/display-buyinfo/:userid" element={<DisplayBuyInfo/>}></Route>
            <Route path="/create-userinfo" element={<CreateUserInfo/>}>
                <Route path="/create-userinfo/join" element={<JoinUser/>}></Route>
                <Route path="/create-userinfo/add-buyinfo" element={<AddBuyInfo/>}></Route>
                <Route path={"/create-userinfo/join/result"} element={<JoinUserResult/>} ></Route>
                <Route path={"/create-userinfo/add-buyinfo/result"} element={<AddBuyInfoResult/>}></Route>
            </Route>
            <Route path={"/logout"} element={<Logout/>}></Route>
          </Route>
        </Routes>
      </BrowserRouter>

  );
}

export default App;
