import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';

export async function GET(request: NextRequest) {

    const token = request.cookies.get("token")?.value;

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const backendResponse = await fetch("http://localhost:8080/quadras", {
        method: 'GET',
        credentials: "include",
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    const data = await backendResponse.json();

    if (backendResponse.ok) {
        return NextResponse.json(data);
    }

    return NextResponse.json({ error: data.error }, { status: backendResponse.status });
}

export async function POST(request: NextRequest) {
    try {
        const token = request.cookies.get("token")?.value;

        if (!token) {
            return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
        }

        const body = await request.json();

        await fetch("http://localhost:8080/quadras", {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer ${token}`
            },
        });

        return NextResponse.json({ success: true }, { status: 201 });
    } catch (error) {
        return NextResponse.json(
            { error: "Internal server error" },
            { status: 500 }
        );
    }
}
