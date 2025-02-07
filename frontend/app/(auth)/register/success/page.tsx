'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { CheckCircle } from 'lucide-react';

export default function RegistrationSuccess() {
    const router = useRouter();

    useEffect(() => {

        const timeoutId = setTimeout(() => {
            sessionStorage.removeItem('registrationSuccess');
            router.push('/login');
        }, 5000);
        return () => {
            clearTimeout(timeoutId);
        }
    }, [router]);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen gap-4">
            <CheckCircle className="h-16 w-16 text-green-600" />
            <h1 className="text-3xl font-bold">Cadastro realizado com sucesso!</h1>
            <p className="text-lg text-center">
                Nós te enviamos um link de confirmação para o seu email.
                <br />
                Redirecionando para a página de login em 5 segundos...
            </p>
            <Button onClick={() => router.push('/login')}>
                Entrar agora
            </Button>
        </div>
    );
}