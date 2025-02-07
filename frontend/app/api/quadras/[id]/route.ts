import { backendClient } from '@/utils/axios';
import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';

export async function GET(
    request: NextRequest,
    { params }: { params: { id: string }}
) {

    const token = request.cookies.get("token")?.value;

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const { id } = await params;

    const backendResponse = await fetch("http://localhost:8080/quadras/" + id, {
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

export async function PUT(
    request: NextRequest,
    { params }: { params: { id: string }}
) {

    const token = request.cookies.get("token")?.value;

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const body = await request.json();
    const { id } = await params;

    const backendResponse = await backendClient(`/quadras/${id}`, {
        method: "PUT",
        data: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${token}`
        }
    });

    // console.log(backendResponse);

    if (backendResponse.status === 200) {
        return NextResponse.json({ success: true }, { status: 200 });
    }

    const data = await backendResponse.data;

    return NextResponse.json({ data }, { status: backendResponse.status });
}
