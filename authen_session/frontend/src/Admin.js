import {useState} from "react";
import apiClient from "./apiInstance";

export default function Admin() {
    const [message, setMessage] = useState(null);

    const handleAdmin = async (e) => {
        try {
            const response = await apiClient.get("/admin", {
                withCredentials: true
            });
            setMessage(response.data);
        } catch (error) {
            if (error.response && error.response.status === 403) {
                setMessage("관리자 계정만 접근 가능합니다.");
            } else {
                console.log(error);
            }
        }
    };

    return (
        <>
            <button onClick={handleAdmin}>Admin</button>
            {message}
        </>
    );
}