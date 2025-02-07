import { TipoDeUsuario } from "@/types/tipo-de-usuario";

export interface UserResponse {
    id: number;
    nome: string;
    sobrenome: string;
    descricao: string;
    email: string;
    telefone: string;
    tipo_de_usuario: TipoDeUsuario;
    verify_account_token: string;
    verified_at: Date;
    created_at: Date;
    updated_at: Date;
    deleted_at: Date;
}
