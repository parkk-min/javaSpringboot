import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8080",
    header: {
        "Content-Type": "application/json"
    },
    timeout: 3000,
});

export default apiClient;