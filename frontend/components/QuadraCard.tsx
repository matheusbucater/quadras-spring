import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "@/components/ui/card";
import Image from "next/image";
import { Quadra } from "@/types/quadra";

export function QuadraCard({ quadra }: { quadra: Quadra }) {
    return (
        <Card className="hover:shadow-lg transition-shadow h-full flex flex-col">
            <CardHeader>
                <CardTitle className="truncate">{quadra.nome}</CardTitle>
                <div className="flex gap-2 text-sm text-muted-foreground">
                    <span>{quadra.tipo_de_quadra}</span>
                    <span>•</span>
                    <span>{quadra.eh_coberta ? "Coberta" : "Descoberta"}</span>
                </div>
            </CardHeader>

            <CardContent className="relative aspect-video flex-1">
                <Image
                    src={quadra.imgurl || "/tennis-court-placeholder.jpg"}
                    alt={quadra.nome}
                    fill
                    className="rounded-md object-cover"
                />
            </CardContent>

            <CardFooter className="flex flex-col items-start gap-2">
                <div className="flex gap-2">
          <span className="px-2 py-1 bg-muted rounded text-sm">
            {quadra.estado_da_quadra}
          </span>
                </div>

                <div className="w-full text-sm">
                    <h4 className="font-medium mb-1">Horário de Funcionamento:</h4>
                    <div className="space-y-1">
                        {quadra.horarios.map((horario, index) => (
                            <div key={index} className="flex justify-between">
                                <span>{horario.dia_da_semana}:</span>
                                <span>
                  {horario.hr_inicio} - {horario.hr_fim}
                </span>
                            </div>
                        ))}
                    </div>
                </div>
            </CardFooter>
        </Card>
    );
}
