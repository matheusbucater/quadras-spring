import { cookies } from "next/headers";

export async function setToken(token: string) {
    const cookieStore = await cookies();
    cookieStore.set("session", token, {
        httpOnly: true,
    });
}

export async function getToken() {
    const cookieStore = await cookies();
    return cookieStore.get("session");
}

export async function removeToken() {
    const cookieStore = await cookies();
    cookieStore.set("session", "", {
        httpOnly: true
    });
}