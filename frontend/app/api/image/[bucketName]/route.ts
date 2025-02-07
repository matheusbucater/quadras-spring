import type { NextRequest } from 'next/server';
import { NextResponse } from 'next/server';

export async function POST(
    request: NextRequest,
    { params }: { params: { bucketName: string }}){

    const token = request.cookies.get("token")?.value;

    if (!token) {
        return NextResponse.json({ error: 'Unauthorized' }, { status: 401 });
    }

    const { bucketName } = await params;
    const incomingFormData = await request.formData();
    const file = incomingFormData.get('file') as File;

    if (!file) {
        return NextResponse.json(
            { error: "No file uploaded" },
            { status: 400 }
        );
    }

    const blob = new Blob([await file.arrayBuffer()], { type: file.type });

    const backendFormData = new FormData();
    backendFormData.append("file", blob, file.name);

    const backendResponse = await fetch(`http://localhost:8080/image/${bucketName}/upload`, {
        method: 'POST',
        body: backendFormData,
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    console.log(token);

    const data = await backendResponse.json();

    if (backendResponse.ok) {
        const { message } = data;
        const url = message.replace('minio', 'localhost');
        return NextResponse.json({ message: url });
    }

    return NextResponse.json({ error: data.error }, { status: backendResponse.status });
}
