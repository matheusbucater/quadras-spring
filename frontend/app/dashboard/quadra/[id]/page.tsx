"use client";

import  { QuadraForm } from "@/components/QuadraForm";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

import { Quadra } from "@/types/quadra";
import { apiClient } from "@/utils/axios";

async function getQuadra(id: string): Promise<Quadra> {

    const res = await apiClient("/quadras/" + id);

    if (res.status !== 200) throw new Error('Failed to fetch quadra');
    return await res.data;
}

export default function QuadraPage() {
    const [quadra, setQuadra] = useState<Quadra>();
    const params: { id: string } = useParams();
    const { id } = params;

    useEffect(() => {
        getQuadra(id).then((data) => {
            setQuadra(data);
        });
    }, []);

    return (
        <div className="container mx-auto py-8">
            <QuadraForm quadra={quadra}/>
        </div>
    );
}

