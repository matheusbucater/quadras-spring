export async function autheticatedFetch(
    input: RequestInfo, 
    init?: RequestInit
): Promise<Response> {

    const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");

    if (tokenExpiresAt) {
        const exp = Number(tokenExpiresAt) * 1000;
        const now = new Date().getTime();
        const diffInHours = (exp - now) / (1000 * 60 * 60);

        if (diffInHours <= 1) {
            const response = await fetch("/api/auth/refreshToken/", {
                method: "POST",
                credentials: "include"
            });

            const data = await response.json();
            if (response.ok) {
                localStorage.setItem("tokenExpiresAt", data.tokenExpiresAt);
            } else {
                console.log("erro ao dar refresh no token:", data);
            }
        }
    }

    const response = await fetch(input, {
        ...init,
        credentials: 'include'
    });

    return response;
}
