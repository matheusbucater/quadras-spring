import { TipoDeUsuario } from "@/types/tipo-de-usuario";

export interface User {
    id: number;
    nome: string;
    sobrenome: string;
    descricao: string;
    email: string;
    telefone: string;
    tipo_de_usuario: TipoDeUsuario;
    updated_at: Date;
}
