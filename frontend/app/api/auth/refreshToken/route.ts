import { extractExpirationClaim } from '@/utils/jwt';
import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';

export async function POST(request: NextRequest) {
    const token = request.cookies.get("token")?.value;

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const backendResponse = await fetch("http://localhost:8080/auth/refresh-token", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        },
    });

    const data = await backendResponse.json();

    if (backendResponse.ok) {
        const newToken = data.token;
        const tokenExpiresAt = extractExpirationClaim(newToken);
        const response = NextResponse.json({ tokenExpiresAt });

        response.cookies.set({
            name: "token",
            value: newToken,
            httpOnly: true,
            path: "/",
            sameSite: "strict",
            maxAge: 60 * 60
        });

        return response;
    }

    const { error } = data;

    return NextResponse.json({ error }, { status: backendResponse.status });
}
