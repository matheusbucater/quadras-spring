"use client";

import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    Form,
    FormField,
    FormItem,
    FormLabel,
    FormControl,
    FormMessage
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useRouter } from "next/navigation";
import Link from "next/link";
import apiClient from "@/utils/axios";

const schema = z.object({
    nome: z.string({ message: "Preencha esse campo." }).min(3, { message: "Mínimo de 3 caracteres" }),
    sobrenome: z.string({ message: "Preencha esse campo." }).min(3, { message: "Mínimo de 3 caracteres" }),
    email: z.string({ message: "Preencha esse campo." }).email({ message: "Email invalido." }).toLowerCase(),
    senha: z.string({ message: "Preencha esse campo." }).min(8, "Mínimo de 8 caracteres"),
    confirmSenha: z.string({ message: "Preencha esse campo." }),
    telefone: z.string({ message: "Preencha esse campo." }).min(10, { message: "Mínimo de 10 caracteres" }),
    descricao: z.string(),
}).superRefine(({ nome, sobrenome }, ctx) => {
    if (nome.match(/\d/) !== null) {
        ctx.addIssue({
            code: "custom",
            message: "Não pode conter números",
            path: ["nome"],
        });
    }
    if (sobrenome.match(/\d/) !== null) {
        ctx.addIssue({
            code: "custom",
            message: "Não pode conter números",
            path: ["sobrenome"],
        });
    }
}).superRefine(({ senha }, ctx) => {
    if (senha.match(/\d/) === null) {
        ctx.addIssue({
            code: "custom",
            message: "- Senha deve conter número",
            path: ["senha"],
        });
    }

    if (senha.match(/[A-Z]/) === null) {
        ctx.addIssue({
            code: "custom",
            message: "- Senha deve conter minúscula",
            path: ["senha"],
        });
    }

    if (senha.match(/[a-z]/) === null) {
        ctx.addIssue({
            code: "custom",
            message: "- Senha deve conter maiúscula",
            path: ["senha"],
        });
    }


}).superRefine(({ senha, confirmSenha }, ctx) => {
    if (senha !== confirmSenha) {
        ctx.addIssue({
            code: "custom",
            message: "Senhas diferentes",
            path: ["confirmSenha"],
        });
    }

});

const defaultValues: z.infer<typeof schema> = {
    nome: "",
    sobrenome: "",
    email: "",
    senha: "",
    confirmSenha: "",
    telefone: "",
    descricao: "",
};

export function RegisterForm() {
    const router = useRouter();
    const [error, setError] = useState("");

    const form = useForm<z.infer<typeof schema>>({
        defaultValues,
        resolver: zodResolver(schema),
    });

    async function onSubmit(values: z.infer<typeof schema>) {

        const response = await apiClient("/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            data: JSON.stringify(values),
        });

        const data = await response.data;

        if (response.status !== 200) {
            switch (data.error) {
                case "usuario_email_already_taken":
                    setError("Email já cadastrado.");
                    return;
                case "usuario_telefone_already_taken":
                    setError("Telefone já cadastrado.");
                    return;
                case "token_creation_failed":
                    setError("Erro ao criar token de verificação.");
                    return;
            }
        }

        console.log(values, data);

        const emailResponse = await apiClient("/api/email/queue", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            data: JSON.stringify({ nome: values.nome, email: values.email, token: data.verify_account_token }),
        });

        const emailData = await emailResponse.data;

        if (response.status === 200) {
            sessionStorage.setItem("showRegistrationSuccessMsg", "true");
            router.push("/register/success");
        }

        if (emailData.error == "token_validation_failed") {
            setError("Erro ao enviar o email de verificação.")
        }
    }

    return (
        <div className="flex flex-col gap-6">
            <Card>
                <CardHeader>
                    <CardTitle className="text-xl">Nova conta</CardTitle>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-6">
                        <Form {...form}>
                            <form
                                onSubmit={form.handleSubmit(onSubmit)}
                                className="space-y-4"
                            >
                                <div className="flex row gap-2">
                                    <FormField
                                        control={form.control}
                                        name="nome"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormControl>
                                                    <Input
                                                        type="text"
                                                        placeholder="Nome"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                    <FormField
                                        control={form.control}
                                        name="sobrenome"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormControl>
                                                    <Input
                                                        type="text"
                                                        placeholder="Sobrenome"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="email"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs text-gray-600">Email</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="email"
                                                        placeholder="m@example.com"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="telefone"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs text-gray-600">Telefone</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="tel"
                                                        placeholder="(XX) XXXXX-XXXX"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="senha"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs text-gray-600">Senha</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="password"
                                                        placeholder="********"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="confirmSenha"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel className="text-xs text-gray-600">Digite Novamente</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="password"
                                                        placeholder="********"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage className="pl-2 text-xs" />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <Button type="submit" className="w-full bg-yellow-500 hover:bg-amber-500">
                                    { "Criar conta" }
                                </Button>
                            </form>
                        </Form>
                        <span className="text-red-500 text-sm">{error}</span>
                    </div>
                    <div className="mt-4 text-center text-sm">
                        { "Já tem uma conta? " }
                        <Link href="/login" className="underline underline-offset-4">
                            { "Entrar" }
                        </Link>
                    </div>
                </CardContent>
            </Card>
        </div>
    )
}
