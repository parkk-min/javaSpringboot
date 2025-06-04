import apiClient from "./apiInstance";
import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {setToken} from "./store";

export default function AdminComponent(){
    const [message, setMessage]= useState(null);
    const dispatch = useDispatch();
    const csrfToken = useSelector(state=>state.token.token);
    const handleAdmin = async (e)=>{
        try{
            const response = await apiClient.get("/admin");
            setMessage(response.data);
        }catch(error){
            if(error.response && error.response.status === 403){
                setMessage(error.response.data);
            }else if(error.response && error.response.status === 401){
                setMessage(error.response.data.message);
            }else{
                console.log(error);
            }
        }
    }

    const handleLogout = (e)=>{
        dispatch(setToken(null));
        setMessage("로그아웃되었습니다.");
    }
    return (
        <>
            <button onClick={handleLogout}>Logout</button>
            <button onClick={handleAdmin}>admin</button>
            {message}
        </>
    );
}