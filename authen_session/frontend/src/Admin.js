import {useState} from "react";
import apiClient from "./apiInstance";
import axios from "axios";
import {useSelector} from "react-redux";

export default function Admin() {
    const [message, setMessage] = useState(null);
    const csrfToken = useSelector(state => state.token.token);

    const handleAdmin = async () => {
        try {
            const response = await apiClient.get("/admin", {
                withCredentials: true
            });
            setMessage(response.data);
        } catch (error) {
            if (error.response && error.response.status === 403) {
                setMessage("관리자 계정만 접근 가능합니다.");
            } else if (error.response && error.response.status === 401) {
                setMessage(error.response.data.message);
            } else {
                console.log(error);
            }
        }
    };

    const handleLogout = async () => {
        try {
            const response = await axios.post("http://localhost:8080/logout", {},
                {
                    headers: {
                        "X-CSRF-TOKEN": csrfToken // 서버로 전송될 CSRF 토큰
                    },
                    withCredentials: true
                });
            setMessage(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <>
            <button onClick={handleLogout}>Logout</button>
            <button onClick={handleAdmin}>Admin</button>
            {message}
        </>
    );
}