import {useRef, useState} from "react";
import apiClient from "./apiInstance";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {setToken} from "./store";

export default function Login(){
    const usernameRef = useRef();
    const passwordRef = useRef();
    const [message, setMessage] = useState(null);
    const navigate = useNavigate();
    const csrfToken = useSelector(state=>state.token.token);
    const dispatch = useDispatch();

    const handleLogin = async (e)=>{
        try{
            const response = await apiClient.post("/login",
                new URLSearchParams({ //x-www-form-urlencoded 형식
                    username:usernameRef.current.value,
                    password:passwordRef.current.value
                }));
            console.log(response.data.role);
            dispatch(setToken(response.headers["authorization"]));
            console.log(response.headers["authorization"]);
            navigate("/admin");
        }catch(error){
            if(error.response && error.response.status === 401){
                setMessage(error.response.data.result);
            }else{
                console.log(error);
            }
        }

    }

    const handleJoin = async (e)=>{
        try{
            const response = await apiClient.post("/join", {
                username: usernameRef.current.value,
                password: passwordRef.current.value
            });
            setMessage(response.data);
        }catch (error){
            if(error.response && error.response.status===409){
                setMessage(error.response.data);
            }else{
                console.log(error);
            }
        }
    }
    return (
        <>
            <form>
                <input type={"text"} ref={usernameRef} name={"username"} placeholder={"아이디입력"}/>
                <input type={"password"} ref={passwordRef} name={"password"} placeholder={"패스워드 입력"}/>
                <button type={"button"} name={"login"} onClick={handleLogin}>Login</button>
                <button type={"button"} name={"join"} onClick={handleJoin}>Join</button>
            </form>
            {message}
        </>
    );
}