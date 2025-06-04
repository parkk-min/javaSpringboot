
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainLayout from "./MainLayout";
import Login from "./Login";
import AdminComponent from "./AdminComponent";
import {useEffect} from "react";
import axios from "axios";
import apiClient from "./apiInstance";
import {useDispatch} from "react-redux";
import {setToken} from "./store";

function App() {
    const dispatch = useDispatch();

  return (
   <>
     <BrowserRouter>
       <Routes>
         <Route path={"/"} element={<MainLayout/>}>
           <Route index element={<Login/>}></Route>
           <Route path={"/admin"} element={<AdminComponent/>}></Route>
         </Route>
       </Routes>
     </BrowserRouter>

   </>
  );
}

export default App;
