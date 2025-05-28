import axios from "axios";
import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import apiClient from "./apiInstance";

export default function LoginComponent() {
    const usernameRef = useRef();
    const passwordRef = useRef();
    const navigate = useNavigate();
    const [message, setMessage] = useState(null);

    const handleLogin = async () => {
        try {
            const response = await axios.post("http://localhost:8080/login",
                new URLSearchParams({
                    username: usernameRef.current.value,
                    password: passwordRef.current.value
                }), {
                    withCredentials: true,
                })
            navigate("/admin");
        } catch (error) {
            if (error.response && error.response.status === 401) {
                setMessage(error.response.data.result);
            } else {
                console.log(error)
            }
        }
    };

    const handleJoin = async () => {
        try {
            const response = await apiClient.post("/join", {
                username: usernameRef.current.value,
                password: passwordRef.current.value,
            });
            setMessage(response.data);
        } catch (error) {
            if (error.response && error.response.status === 409) {
                setMessage(error.response.data);
            } else {
                console.log(error);
            }
        }
    };

    return (
        <>
            <form>
                <input type={"text"} ref={usernameRef} name={"username"} placeholder={"아이디 입력"}/>
                <input type={"text"} ref={passwordRef} name={"password"} placeholder={"비밀번호 입력"}/>
                <button type={"button"} name={"login"} onClick={handleLogin}>Login</button>
                <button type={"button"} name={"join"} onClick={handleJoin}>Join</button>
            </form>
            {message}
        </>
    );
}