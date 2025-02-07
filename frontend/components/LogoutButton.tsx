"use client";

import { Button } from '@/components/ui/button';
import { apiClient } from '@/utils/axios';
import {useRouter} from "next/navigation";

export function LogoutButton() {
    const router = useRouter();

    const handleLogout = async  () => {
        await apiClient("http://localhost:3000/api/auth/logout", { method: "POST" });
        localStorage.removeItem("user");
        localStorage.removeItem("tokenExpiresAt");
        router.push("/login");
    };

    return <Button onClick={handleLogout}>Logout</Button>;
}
