import { NextResponse } from 'next/server';

export async function POST(request: Request) {
    const { nome, sobrenome, email, senha, telefone } = await request.json();

    const backendResponse = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, sobrenome, email, senha, telefone }),
    });

    const data = await backendResponse.json();

    console.log(data);

    if (!backendResponse.ok) {
        return NextResponse.json({ error: data.error }, { status: backendResponse.status });
    }

    return NextResponse.json(data);
}