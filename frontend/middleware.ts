import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';
import { extractRoleClaim, extractExpirationClaim } from './utils/jwt';
import { TipoDeUsuario } from './types/tipo-de-usuario';

export function middleware(req: NextRequest) {
    const token = req.cookies.get('token')?.value;

    if (req.nextUrl.pathname === '/login' && token) {
        return NextResponse.redirect(new URL('/dashboard', req.url));
    }

    const protectedRoutes = ['/dashboard', '/profile'];
    const adminRoutes = ['/dashboard/create']

    if (req.nextUrl.pathname.startsWith("/register/success")) {
        if (!req.headers.get("referer")?.includes("/register")) {
            return NextResponse.redirect(new URL('/register', req.url));
        }
    }

    if (protectedRoutes.some(route => req.nextUrl.pathname.startsWith(route))) {
        if (!token) {
            const incomingUrl = encodeURIComponent(req.nextUrl.pathname);
            return NextResponse.redirect(new URL(`/login?from=${incomingUrl}`, req.url));
        }
    }

    if (adminRoutes.some(route => req.nextUrl.pathname.startsWith(route))) {
        const role = extractRoleClaim(token || "");

        if (role != TipoDeUsuario.ADMINISTRADOR && role != TipoDeUsuario.SUPERVISOR) {
            return NextResponse.redirect(new URL("/dashboard", req.url));
        }
    }

    return NextResponse.next();
}

export const config = {
    matcher: [
        "/register/success",
        "/login",
        "/dashboard/:path*",
    ],
};
