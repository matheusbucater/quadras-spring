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
import { useRouter, useSearchParams } from "next/navigation";
import Link from "next/link";
import { apiClient } from "@/utils/axios";

const schema = z.object({
    email: z.string({ message: "Preencha esse campo." }).email({ message: "Email invalido." }).toLowerCase(),
    senha: z.string({ message: "Preencha esse campo." }),
});

const defaultValues: z.infer<typeof schema> = {
    email: "",
    senha: ""
};

export function LoginForm() {
    const router = useRouter();
    const searchParams = useSearchParams();
    const [error, setError] = useState("");

    const form = useForm<z.infer<typeof schema>>({
        defaultValues,
        resolver: zodResolver(schema),
    });

    async function onSubmit(values: z.infer<typeof schema>) {

        const response = await apiClient("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            data: JSON.stringify(values),
        });

        const data = await response.data;

        if (response.status === 200) {
            localStorage.setItem("user", JSON.stringify(data.user));
            localStorage.setItem("tokenExpiresAt", data.tokenExpiresAt);
            const from = decodeURIComponent(searchParams.get("from") || "");

            console.log("from", from);

            if (!from || ["/logout", "/login", "/register"].some((route) => from.startsWith(route))) {
                router.push("/dashboard");
            }

            router.push(from);
        }

        if (data.error == "authentication" || data.error == "usuario_soft_deleted") {
            setError("Email ou senha incorretos.")
        } else if (data.error == "usuario_not_verified") {
            setError("Usuário não verificado.")
        }
    }

    return (
        <div className="flex flex-col gap-6">
            <Card>
                <CardHeader>
                    <CardTitle className="text-xl">
                        Entre com seu email e senha.
</CardTitle>
                    {/*<CardDescription>*/}
                    {/*</CardDescription>*/}
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-6">
                        <Form {...form}>
                            <form
                                onSubmit={form.handleSubmit(onSubmit)}
                                className="space-y-4"
                            >
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="email"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Email</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="email"
                                                        placeholder="m@example.com"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage />
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
                                                <FormLabel>Senha</FormLabel>
                                                <FormControl>
                                                    <Input
                                                        type="password"
                                                        placeholder="********"
                                                        {...field}
                                                        required
                                                    />
                                                </FormControl>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    >
                                    </FormField>
                                </div>
                                <Button type="submit" className="w-full bg-yellow-500 hover:bg-amber-500">
                                    { "Entrar" }
                                </Button>
                            </form>
                        </Form>
                        <span className="text-red-500 text-sm">{error}</span>
                    </div>
                    <div className="mt-4 text-center text-sm">
                        { "Não tem uma conta? " }
                        <Link href="/register" className="underline underline-offset-4">
                            { "Cadastre-se" }
                        </Link>
                    </div>
                </CardContent>
            </Card>
        </div>
    )
}
