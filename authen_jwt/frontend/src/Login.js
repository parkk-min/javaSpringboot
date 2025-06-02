import {useRef, useState} from "react";
import apiClient from "./apiInstance";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {setToken} from "./store";

export default function Login() {
    const usernameRef = useRef();
    const passwordRef = useRef();
    const [message, setMessage] = useState(null);
    const navigate = useNavigate();
    const csrfToken = useSelector(state => state.token.token);
    const dispatch = useDispatch();

    const handleLogin = async () => {
        try {
            // 백엔드의 /login 엔드포인트로 POST 요청 전송
            const response = await axios.post("http://localhost:8080/login",
                new URLSearchParams({ // x-www-form-urlencoded 형식으로 데이터 전송
                    username: usernameRef.current.value, // 입력된 사용자 ID
                    password: passwordRef.current.value // 입력된 비밀번호
                }), {
                    headers: {
                        "X-CSRF-TOKEN": csrfToken // 서버로 전송될 CSRF 토큰
                    },
                    withCredentials: true // 세션 쿠키를 포함하여 요청 (Spring Security와 세션 인증을 위해 필요)
                });
            dispatch(setToken(response.data["csrf-token"]));
            console.log(response.data["csrf-token"]);
            // 로그인 성공 시, 응답 데이터에서 역할(role)을 메시지로 설정
            // setMessage(response.data.role[0].authority);
            navigate("/admin"); // 로그인 성공 후 /admin 페이지로 이동
        } catch (error) {
            if (error.response && error.response.status === 401) {
                // 401 Unauthorized 에러: 인증 실패 시 서버에서 반환한 메시지 표시
                setMessage(error.response.data.result);
            } else {
                console.log(error);
            }
        }
    };

    const handleJoin = async () => {
        try {
            // 백엔드의 /join 엔드포인트로 POST 요청 전송 (apiClient 사용)
            const response = await apiClient.post("/join", {
                username: usernameRef.current.value, // 입력된 사용자 ID
                password: passwordRef.current.value // 입력된 비밀번호
            }, {
                headers: {
                    "X-CSRF-TOKEN": csrfToken
                },
                withCredentials: true
            });
            setMessage(response.data);
        } catch (error) {
            if (error.response && error.response.status === 409) {
                // 409 Conflict 에러: 이미 존재하는 사용자일 경우 서버에서 반환한 메시지 표시
                setMessage(error.response.data);
            } else {
                console.log(error);
            }
        }
    };

    return (
        <>
            <form>
                <input type={"text"} ref={usernameRef} name={"username"} placeholder={"아이디를 입력하세요."}/>
                <input type={"password"} ref={passwordRef} name={"password"} placeholder={"비밀번호를 입력하세요."}/>
                <button type={"button"} name={"login"} onClick={handleLogin}>Login</button>
                <button type={"button"} name={"join"} onClick={handleJoin}>Join</button>
            </form>
            {message}
        </>
    );
}