import { TipoDeUsuario } from '@/types/tipo-de-usuario';
import jwt from 'jsonwebtoken';
import { jwtDecode } from "jwt-decode";
import { type StringValue } from "ms";

const SECRET_KEY = process.env.SECRET_KEY || "";

interface Claims {
    iss: string,
    sub: string,
    purpose: string,
    role: TipoDeUsuario,
    ext: string,
    exp: Date
}

export function generateEmailToken(addr_to: string, subject: string, body: string, expiresIn: StringValue = '1h') {
  return jwt.sign({ addr_to, subject, body, iss: "qsmc-auth-api" }, SECRET_KEY, { expiresIn });
}

export function extractRoleClaim(token: string) {
    const { role } = jwtDecode<Claims>(token);
    return role;
}

export function extractExpirationClaim(token: string) {
    const { exp } = jwtDecode<Claims>(token);
    return exp;
}
