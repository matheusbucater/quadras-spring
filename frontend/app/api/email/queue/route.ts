import { NextResponse } from 'next/server';
import { generateEmailToken } from '@/utils/jwt'
import React from "react";
import { EmailTemplate } from "@/components/EmailTemplate";

export async function POST(request: Request){
    const { nome, email, token } = await request.json();
    console.log(nome, email, token);
    const link = `http://localhost:3000/register/verify/${token}`;

    const ReactDOMServer = (await import("react-dom/server")).default

    const emailBody = ReactDOMServer.renderToStaticMarkup(
        React.createElement(
            EmailTemplate,
            {
                name: nome,
                purposeMessage: "Aqui está seu link de verificação",
                actionLink: link,
                footerMessage: "Se você já verificou sua conta, ignore este email."
            }
        )
    );
    const emailToken = generateEmailToken(email, "Verify new Account", emailBody);

    const backendResponse = await fetch(`http://localhost:8080/email/queue?token=${emailToken}`, {
        method: 'GET'
    });
    const data = await backendResponse.json();

    if (backendResponse.ok) {
        return NextResponse.json(data);
    }

    return NextResponse.json({ error: data.error }, { status: backendResponse.status });
}
