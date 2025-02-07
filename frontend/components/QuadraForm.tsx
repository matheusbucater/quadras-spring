"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { Card, CardHeader, CardTitle, CardDescription, CardContent } from "@/components/ui/card";
import { z } from "zod";
import { useRouter } from "next/navigation";
import { Textarea } from "@/components/ui/textarea";
import { ImageUploader } from "@/components/ImageUpload";
import { apiClient } from "@/utils/axios";
import { Quadra } from "@/types/quadra";
import { useEffect } from "react";

// Zod schema based on Quadra interface
const formSchema = z.object({
    nome: z.string().min(2, "Nome deve ter pelo menos 2 caracteres"),
    imgurl: z.string().url("URL inválida").min(1, "Imagem obrigatória"),
    descricao: z.string().optional(),
    estado_da_quadra: z.enum(["DISPONIVEL", "EM_REFORMA"]),
    tipo_de_quadra: z.enum(["SAIBRO", "DURA", "GRAMA", "TENIS"]),
    eh_coberta: z.boolean().default(false),
    // horarios: z.array(z.object({
    //     dia_da_semana: z.enum(["SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO", "FERIADO"]),
    //     hr_inicio: z.string().regex(/^\d{2}:\d{2}$/, "Formato HH:MM"),
    //     hr_fim: z.string().regex(/^\d{2}:\d{2}$/, "Formato HH:MM")
    // })).min(1, "Pelo menos um horário necessário")
});

interface QuadraFormProps {
    quadra?: Quadra;
}

export function QuadraForm({ quadra }: QuadraFormProps) {

    const defaultValues: z.infer<typeof formSchema> = {
        nome: quadra?.nome || "",
        imgurl: quadra?.imgurl || "",
        estado_da_quadra: quadra?.estado_da_quadra || "DISPONIVEL",
        tipo_de_quadra: quadra?.tipo_de_quadra || "SAIBRO",
        eh_coberta: quadra?.eh_coberta || false,
        horarios: quadra?.horarios || [{ dia_da_semana: "SEGUNDA", hr_inicio: "08:00", hr_fim: "18:00" }]
    }

    const router = useRouter();
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues
    });

    useEffect(() => {
        if (quadra) {
            form.reset(quadra);
        }
    }, [quadra]);

    useEffect(() => {
        console.log("Form errors", form.formState.errors);
    }, [form.formState.errors]);

    async function onSubmit(values: z.infer<typeof formSchema>) {
        console.log("values", values);
        try {

            console.log("quadra", quadra, Boolean(quadra));
            if (quadra) {
                console.log("autaliazar quadra");
                const response = await apiClient(`/quadras/${quadra.id}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    data: JSON.stringify(values)
                });
                if (response.status !== 200) throw new Error("Falha ao atualizar quadra");
                router.push("/dashboard");
            } else {
                const response = await apiClient("/quadras", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    data: JSON.stringify(values)
                });
                if (response.status !== 200) throw new Error("Falha ao criar quadra");
                router.push("/dashboard");
            }
        } catch (error) {
            console.error("Erro:", error);
        }
    }

    return (
        <Card className="max-w-3xl mx-auto">
            <CardHeader>
                <CardTitle>
                {
                    (quadra == null)
                    ? "Criar Nova Quadra"
                    : quadra.nome
                }
                </CardTitle>
                <CardDescription>
                {
                    (quadra == null)
                    ? "Preencha os detalhes da nova quadra esportiva"
                    : "Preencha os detalhes da quadra"
                }
                </CardDescription>
            </CardHeader>
            <CardContent>
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                        {/* Image Upload Section */}
                        <FormField
                            control={form.control}
                            name="imgurl"
                                render={({ field }) => (
                                    <FormItem className="flex flex-row justify-center">
                                        <ImageUploader
                                            bucketName="quadras"
                                            url={field.value}
                                            onUploadCallback={(url) => {
                                                field.onChange(url);
                                                form.setValue("imgurl", url);
                                            }}
                                        />
                                    </FormItem>
                            )}
                        />
                        {/* Cobertura */}
                        <FormField
                            control={form.control}
                            name="eh_coberta"
                            render={({ field }) => (
                                <FormItem className="flex flex-row items-center justify-between rounded-lg border p-4">
                                    <div className="space-y-0.5">
                                        <FormLabel>A quadra é coberta?</FormLabel>
                                    </div>
                                    <FormControl>
                                        <Switch
                                            checked={field.value}
                                            onCheckedChange={field.onChange}
                                        />
                                    </FormControl>
                                </FormItem>
                            )}
                        />

                        {/* Nome */}
                        <FormField
                            control={form.control}
                            name="nome"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Nome da Quadra</FormLabel>
                                    <FormControl>
                                        <Input placeholder="Quadra 1" {...field} />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        {/* Tipo de Quadra */}
                        <FormField
                            control={form.control}
                            name="tipo_de_quadra"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Tipo de Quadra</FormLabel>
                                    <Select onValueChange={field.onChange} value={field.value}>
                                        <FormControl>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Selecione o tipo" />
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent>
                                            <SelectItem value="SAIBRO">Saibro</SelectItem>
                                            <SelectItem value="DURA">Dura</SelectItem>
                                            <SelectItem value="GRAMA">Grama</SelectItem>
                                        </SelectContent>
                                    </Select>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        {/* Estado da Quadra */}
                        <FormField
                            control={form.control}
                            name="estado_da_quadra"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Estado da Quadra</FormLabel>
                                    <Select onValueChange={field.onChange} value={field.value}>
                                        <FormControl>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Selecione o estado" />
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent>
                                            <SelectItem value="DISPONIVEL">Disponível</SelectItem>
                                            <SelectItem value="EM_REFORMA">Reforma</SelectItem>
                                        </SelectContent>
                                    </Select>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        {/* Descrição */}
                        <FormField
                            control={form.control}
                            name="descricao"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Descrição (Opcional)</FormLabel>
                                    <FormControl>
                                        <Textarea
                                            placeholder="Detalhes sobre a quadra..."
                                            className="resize-none"
                                            {...field}
                                        />
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        {/*/!* Imagem URL *!/*/}
                        {/*<FormField*/}
                        {/*    control={form.control}*/}
                        {/*    name="imgurl"*/}
                        {/*    render={({ field }) => (*/}
                        {/*        <FormItem>*/}
                        {/*            <FormLabel>URL da Imagem</FormLabel>*/}
                        {/*            <FormControl>*/}
                        {/*                <Input placeholder="https://exemplo.com/imagem.jpg" {...field} />*/}
                        {/*            </FormControl>*/}
                        {/*            <FormMessage />*/}
                        {/*        </FormItem>*/}
                        {/*    )}*/}
                        {/*/>*/}


                        {/* Horários */}
                        {/*<div className="space-y-4">*/}
                        {/*    <FormLabel>Horários de Funcionamento</FormLabel>*/}
                        {/*    {fields.map((field, index) => (*/}
                        {/*        <div key={field.id} className="flex gap-4 items-end">*/}
                        {/*            <FormField*/}
                        {/*                control={form.control}*/}
                        {/*                name={`horarios.${index}.dia_da_semana`}*/}
                        {/*                render={({ field }) => (*/}
                        {/*                    <FormItem className="flex-1">*/}
                        {/*                        <Select onValueChange={field.onChange} value={field.value}>*/}
                        {/*                            <FormControl>*/}
                        {/*                                <SelectTrigger>*/}
                        {/*                                    <SelectValue placeholder="Dia" />*/}
                        {/*                                </SelectTrigger>*/}
                        {/*                            </FormControl>*/}
                        {/*                            <SelectContent>*/}
                        {/*                                {[*/}
                        {/*                                    "SEGUNDA", "TERCA", "QUARTA", "QUINTA",*/}
                        {/*                                    "SEXTA", "SABADO", "DOMINGO", "FERIADO"*/}
                        {/*                                ].map((dia) => (*/}
                        {/*                                    <SelectItem key={dia} value={dia}>*/}
                        {/*                                        {{*/}
                        {/*                                            SEGUNDA: "Segunda",*/}
                        {/*                                            TERCA: "Terça",*/}
                        {/*                                            QUARTA: "Quarta",*/}
                        {/*                                            QUINTA: "Quinta",*/}
                        {/*                                            SEXTA: "Sexta",*/}
                        {/*                                            SABADO: "Sábado",*/}
                        {/*                                            DOMINGO: "Domingo",*/}
                        {/*                                            FERIADO: "Feriados"*/}
                        {/*                                        }[dia]}*/}
                        {/*                                    </SelectItem>*/}
                        {/*                                ))}*/}
                        {/*                            </SelectContent>*/}
                        {/*                        </Select>*/}
                        {/*                        <FormMessage />*/}
                        {/*                    </FormItem>*/}
                        {/*                )}*/}
                        {/*            />*/}

                        {/*            <FormField*/}
                        {/*                control={form.control}*/}
                        {/*                name={`horarios.${index}.hr_inicio`}*/}
                        {/*                render={({ field }) => (*/}
                        {/*                    <FormItem>*/}
                        {/*                        <FormControl>*/}
                        {/*                            <Input type="time" {...field} />*/}
                        {/*                        </FormControl>*/}
                        {/*                        <FormMessage />*/}
                        {/*                    </FormItem>*/}
                        {/*                )}*/}
                        {/*            />*/}

                        {/*            <FormField*/}
                        {/*                control={form.control}*/}
                        {/*                name={`horarios.${index}.hr_fim`}*/}
                        {/*                render={({ field }) => (*/}
                        {/*                    <FormItem>*/}
                        {/*                        <FormControl>*/}
                        {/*                            <Input type="time" {...field} />*/}
                        {/*                        </FormControl>*/}
                        {/*                        <FormMessage />*/}
                        {/*                    </FormItem>*/}
                        {/*                )}*/}
                        {/*            />*/}

                        {/*            <Button*/}
                        {/*                type="button"*/}
                        {/*                variant="destructive"*/}
                        {/*                size="sm"*/}
                        {/*                onClick={() => remove(index)}*/}
                        {/*            >*/}
                        {/*                Remover*/}
                        {/*            </Button>*/}
                        {/*        </div>*/}
                        {/*    ))}*/}

                        {/*    <Button*/}
                        {/*        type="button"*/}
                        {/*        variant="outline"*/}
                        {/*        size="sm"*/}
                        {/*        onClick={() => append({ dia_da_semana: "SEGUNDA", hr_inicio: "08:00", hr_fim: "18:00" })}*/}
                        {/*    >*/}
                        {/*        Adicionar Horário*/}
                        {/*    </Button>*/}
                        {/*</div>*/}

                        <Button type="submit" className="w-full">
                        {
                            (quadra == null)
                            ? "Criar Quadra"
                            : "Atualizar Quadra"
                        }
                        </Button>
                    </form>
                </Form>
            </CardContent>
        </Card>
    );
}
