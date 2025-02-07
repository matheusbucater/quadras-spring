import { User } from '@/types/user';
import { UserResponse } from '@/types/user-response';
import { extractExpirationClaim } from "@/utils/jwt";
import axios from 'axios';
import { NextResponse } from 'next/server';
import { backendClient } from "@/utils/axios";

export async function POST(request: Request) {
    const { email, senha } = await request.json();

    const backendResponse = await backendClient('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: JSON.stringify({ email, senha })
    });

    const data = await backendResponse.data;

    if (backendResponse.status !== 200) {

        const { error } = data;

        return NextResponse.json({ error }, { status: backendResponse.status });
    }

    const { token } = data;

    const usuarioResponse = await backendClient("http://localhost:8080/usuarios/self", {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    const usuarioData = await usuarioResponse.data;

    if (usuarioResponse.status !== 200) {

        const { error } = usuarioData;

        return NextResponse.json({ error }, { status: backendResponse.status });
    }

    const usuario: User = {
        id: usuarioData.id,
        nome: usuarioData.nome,
        sobrenome: usuarioData.nome,
        email: usuarioData.email,
        telefone: usuarioData.telefone,
        descricao: usuarioData.descricao,
        tipo_de_usuario: usuarioData.tipo_de_usuario,
        updated_at: usuarioData.updated_at
    };

    const tokenExpiresAt = extractExpirationClaim(token);

    const response = NextResponse.json({ user: usuario, tokenExpiresAt });
    response.cookies.set({
        name: "token",
        value: token,
        httpOnly: true,
        path: "/",
        sameSite: "strict",
        maxAge: 60 * 60
    });

    return response;
}
