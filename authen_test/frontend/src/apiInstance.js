import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
        "Content-Type": "application/json"
    },
    timeout: 3000,
});

export default apiClient;