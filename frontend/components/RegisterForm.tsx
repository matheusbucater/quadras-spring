"use client"; // Mark this as a Client Component

import React, { useEffect, useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from '@/components/ui/card';
import { useRouter } from 'next/navigation';
import apiClient from '@/utils/axios';

export function RegisterForm() {
    const [nome, setNome] = useState('');
        const [sobrenome, setSobrenome] = useState('');
        const [email, setEmail] = useState('');
        const [senha , setSenha] = useState('');
        const [errorSenha, setErrorSenha] = useState('');
        const [senha2 , setSenha2] = useState('');
        const [errorSenha2, setErrorSenha2] = useState('');
        const [telefone, setTelefone] = useState('');
        const [error, setError] = useState('');
        const router = useRouter();

        const validaSenha2 = () => {
            if (senha2) {
                if (senha2 !== senha)
                    setErrorSenha2("As senhas não são iguais.");
                if (senha2 === senha)
                    setErrorSenha2("");
            }
        }

        const validaSenha = () => {
            // TODO! - validar senha
        }

        useEffect(() => {
            validaSenha();
            validaSenha2();
        }, [senha, senha2]);

        const handleSubmit = async (e: React.FormEvent) => {
            e.preventDefault();

            const response = await apiClient('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                data: JSON.stringify({ nome, sobrenome, email, senha, telefone }),
            });

            const data = await response.data;

            if (response.status === 200) {
                const token = data.verify_account_token;

                await apiClient('/api/email/queue', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                data: JSON.stringify({ nome, email, token }),
            })

            sessionStorage.setItem("registrationSuccess", "true");

            router.push('/register/success');
        }

        if (data.error == 'usuario_email_already_taken') {
            setError("Email já cadastrado.")
        } else if (data.error == 'usuario_telefone_already_taken') {
            setError("Telefone já cadastrado.")
        }
    };

    return (
        <Card className="w-full max-w-sm">
            <CardHeader>
                <CardTitle className="text-2xl">Cadastro</CardTitle>
                <CardDescription>Criar uma nova conta.</CardDescription>
            </CardHeader>
            <CardContent>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="flex space-x-4">
                        <div className="flex-1 space-y-2">
                            <Label htmlFor="nome">Nome</Label>
                            <Input
                                id="nome"
                                type="text"
                                placeholder="Nome"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                required
                            />
                        </div>
                        <div className="flex-1 space-y-2">
                            <Label htmlFor="sobrenome">Sobrenome</Label>
                            <Input
                                id="sobrenome"
                                type="text"
                                placeholder="Sobrenome"
                                value={sobrenome}
                                onChange={(e) => setSobrenome(e.target.value)}
                                required
                            />
                        </div>
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="email">Email</Label>
                        <Input
                            id="email"
                            type="email"
                            placeholder="m@example.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="password">Senha</Label>
                        <Input
                            id="password"
                            type="password"
                            placeholder="********"
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                            required
                        />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="password2">Confirmar senha</Label>
                        <Input
                            id="password2"
                            type="password"
                            placeholder="********"
                            value={senha2}
                            onChange={(e) => setSenha2(e.target.value)}
                            required
                        />
                        {errorSenha2 && <p className="text-red-500 text-sm">{errorSenha2}</p>}
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="telefone">Telefone</Label>
                        <Input
                            id="telefone"
                            type="text"
                            placeholder="Telefone"
                            value={telefone}
                            onChange={(e) => setTelefone(e.target.value)}
                            required
                        />
                    </div>
                    {error && <p className="text-red-500 text-sm">{error}</p>}
                    <Button type="submit" className="w-full">
                        Criar conta
                    </Button>
                </form>
            </CardContent>
            <CardFooter className="text-sm">
                <a href="/login" className="text-primary hover:underline">
                    Já tem uma conta?
                </a>
            </CardFooter>
        </Card>
    );
}
