"use client";

import { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";

export default function VerifyPage() {
    const router = useRouter();
    const { token } = useParams();

    const [status, setStatus] = useState<"loading" | "success" | "error">("loading");
    const [error, setError] = useState<string>();

    useEffect(() => {
        if (!token) return;

        const verifyUser = async () => {
            const backendResponse = await fetch(
                "/api/auth/verifyAccount", {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ token }),
                }
            );

            const data  = await backendResponse.json();

            if (backendResponse.ok) {
                setStatus("success");
                return
            }

            setStatus("error");

            if (data.error == "usuario_already_verified") {
                setError("Usuario já verificado!")
                return;
            } else {
                setError("Token inválido.")
                return;
            }
        };

        verifyUser().then(() => {
            setTimeout(() => {
                router.push("/login")
            }, 5000);
        });
    }, [token, router]);

    return (
        <div className="flex flex-col items-center justify-center h-screen text-center">
            {status === "loading" && <p>Ativando sua conta...</p>}
            {status === "success" && <p>Verificação feita com sucesso!<br/>Redirecionando para a página de login...</p>}
            {status === "error" && <p>Erro - {error}</p>}
        </div>
    );
}


// 'use client';
//
// import { useEffect } from 'react';
// import { useRouter } from 'next/router';
// import { Button } from '@/components/ui/button';
// import { CheckCircle } from 'lucide-react';
//
// export default function RegistrationSuccess() {
//     const router = useRouter();
//     const token = router.query.token as string;
//
//     useEffect(() => {
//
//         const verificationSuccess = sessionStorage.getItem('verificationSuccess');
//         if (!verificationSuccess) {
//             router.push('/register');
//             return;
//         }
//
//         const timeoutId = setTimeout(() => router.push('/login'), 5000);
//         return () => {
//             clearTimeout(timeoutId);
//             sessionStorage.removeItem("verificationSuccess");
//         }
//     }, [router]);
//
//     return (
//         <div className="flex flex-col items-center justify-center min-h-screen gap-4">
//             <CheckCircle className="h-16 w-16 text-green-600" />
//             <h1 className="text-3xl font-bold">Email verificado com sucesso!</h1>
//             <p className="text-lg text-center">
//                 Redirecionando para a página de login em 5 segundos...
//             </p>
//             <Button onClick={() => router.push('/login')}>
//                 Entrar agora
//             </Button>
//         </div>
//     );
// }