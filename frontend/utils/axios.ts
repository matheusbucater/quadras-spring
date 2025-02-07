import axios, { AxiosError } from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:3000/api",
    withCredentials: true,
    validateStatus: () => true,
});
const backendClient = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,
    validateStatus: () => true,
});

apiClient.interceptors.request.use(
    async (config) => {
        const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");

        if (tokenExpiresAt) {
            const exp = Number(tokenExpiresAt) * 1000;
            const now = new Date().getTime();
            const diffInHours = (exp - now) / (1000 * 60 * 60);

            if (diffInHours <= 1) {
                const response = await axios("/api/auth/refreshToken/", {
                    method: "POST",
                    withCredentials: true,
                });

                const data = await response.data;

                if (response.status === 200) {
                    localStorage.setItem("tokenExpiresAt", data.tokenExpiresAt);
                } else {
                    console.log("erro ao dar refresh no token:", data);
                }
            }
        }
        return config;
    },
    (e) => {
        const error = AxiosError.from(e);
        return error.response;
    }
);

export { apiClient, backendClient };
