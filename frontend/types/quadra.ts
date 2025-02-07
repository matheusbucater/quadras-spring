import { QuadraHorario } from "@/types/quadra-horario";

export interface Quadra {
    id: number;
    nome: string;
    imgurl: string;
    descricao?: string;
    estado_da_quadra: string;
    tipo_de_quadra: string;
    eh_coberta: boolean;
    horarios: QuadraHorario[];
}
