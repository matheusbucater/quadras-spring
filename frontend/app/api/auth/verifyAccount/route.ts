import { NextResponse } from "next/server";

export async function PUT(request: Request) {

    const { token } = await request.json();

    const backendResponse = await fetch("http://localhost:8080/auth/verify-account", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ token }),
    });

    const data = await backendResponse.json();

    if (backendResponse.ok) {
        return NextResponse.json(data);
    }

    return NextResponse.json({ error: data.error }, { status: backendResponse.status })
}