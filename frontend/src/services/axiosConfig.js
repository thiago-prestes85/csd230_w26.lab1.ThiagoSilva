import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "https://csd230-w26-lab1-thiagosilva.onrender.com"
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            window.location.href = "/login?expired=true";
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;