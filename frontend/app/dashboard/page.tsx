"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

import { Quadra } from "@/types/quadra";

import { apiClient } from "@/utils/axios";

import { Button } from "@/components/ui/button";
import { QuadraCard } from "@/components/QuadraCard";
import { Input } from "@/components/ui/input";
import { User } from "@/types/user";
import { TipoDeUsuario } from "@/types/tipo-de-usuario";
import Link from "next/link";

async function getQuadras(): Promise<Quadra[]> {

    const res = await apiClient("/quadras");

    if (res.status !== 200) throw new Error('Failed to fetch quadras');
    return await res.data;
}

export default function DashboardPage() {

    const router = useRouter();
    const [quadras, setQuadras] = useState<Quadra[]>([]);
    const [user, setUser] = useState<User>();

    useEffect(() => {
        const usuario: User = JSON.parse(localStorage.getItem("user") || "");

        if (!usuario) {
            throw new Error("unauthorized");
        }

        setUser(usuario);
        getQuadras().then(quadrasResponse => setQuadras(quadrasResponse));

    }, []);

    return (
        <div className="p-6 space-y-6">
            <h1>Hi! {user?.nome}</h1>
            {(user?.tipo_de_usuario == TipoDeUsuario.ADMINISTRADOR || user?.tipo_de_usuario == TipoDeUsuario.SUPERVISOR) &&
                <Button onClick={() => router.push("/dashboard/create")}>Novo quadra</Button>
            }
            <div className="max-w-md">
                <Input placeholder="Pesquisar quadras..." />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {quadras.map((quadra) => (
                    <Link key={quadra.id} href={`/dashboard/quadra/${quadra.id}`}>
                        <QuadraCard key={quadra.id} quadra={quadra} />
                    </Link>
                ))}
            </div>

            {quadras.length === 0 && (
                <div className="text-center text-muted-foreground py-12">
                    Nenhuma quadra encontrada
                </div>
            )}
        </div>
    );
}
