"use client";

import { useCallback, useEffect, useState } from "react";
import { useDropzone } from "react-dropzone";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { UploadCloud, X, Loader2, CheckCircle2, AlertCircle } from "lucide-react";
import Image from "next/image";
import { apiClient } from "@/utils/axios";

interface ImageUploadProps {
    bucketName: string;
    url: string;
    onUploadCallback: (url: string) => void;
}

export function ImageUploader({ bucketName, url, onUploadCallback }: ImageUploadProps) {
    const [file, setFile] = useState<File | null>(null);
    const [preview, setPreview] = useState<string>("");
    const [isUploading, setIsUploading] = useState(false);
    const [serverUrl, setServerUrl] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        if (url) {
            setServerUrl(url);
            setPreview(url);
        }
    }, [url]);

    const onDrop = useCallback((acceptedFiles: File[]) => {
        const file = acceptedFiles[0];
        if (file) {
            setFile(file);
            setPreview(URL.createObjectURL(file));
            setServerUrl("");
            setError("");
        }
    }, []);

    const { getRootProps, getInputProps, isDragActive, isDragReject } = useDropzone({
        onDrop,
        accept: { 'image/*': ['.png', '.jpg', '.jpeg', '.gif', '.webp'] },
        multiple: false,
        maxSize: 5 * 1024 * 1024 // 5MB
    });

    const handleUpload = async () => {
        if (!file) return;

        setIsUploading(true);
        setError("");

        try {
            const formData = new FormData();
            formData.append("file", file);

            const response = await apiClient(`/image/${bucketName}`, {
                method: 'POST',
                data: formData
            });

            const data: { message: string } = await response.data;
            const url = data.message;

            setServerUrl(url);
            onUploadCallback(url);
        } catch {
            setError("Upload failed. Please try again.");
        } finally {
            setIsUploading(false);
        }
    };

    const removeFile = () => {
        setFile(null);
        setPreview("");
        setServerUrl("");
        setError("");
        if (preview) URL.revokeObjectURL(preview);
    };

    return (
        <Card className="p-4 w-full max-w-xs"> {/* Reduced padding and max width */}
            <div
                {...getRootProps()}
                className={`border-2 border-dashed rounded-lg p-4 text-center cursor-pointer
          ${isDragActive ? "border-primary bg-primary/10" : "border-muted-foreground/50"}
          ${isDragReject ? "border-destructive bg-destructive/10" : ""}`}
            >
                <input {...getInputProps()} />

                <div className="relative w-full h-48"> {/* Fixed height instead of aspect-square */}
                    {!preview ? (
                        <div className="absolute inset-0 flex flex-col items-center justify-center gap-2">
                            <UploadCloud className={`h-8 w-8 ${isDragActive ? "text-primary" : "text-muted-foreground"}`} />
                            <div className="space-y-1">
                                <p className="text-xs font-medium"> {/* Smaller text */}
                                    {isDragActive ? "Drop image here" : "Drag & drop or click"}
                                </p>
                                <p className="text-[0.7rem] text-muted-foreground"> {/* Smaller text */}
                                    PNG, JPG, JPEG, GIF, WEBP (Max 5MB)
                                </p>
                                {isDragReject && (
                                    <p className="text-[0.7rem] text-destructive">Invalid file</p>
                                )}
                            </div>
                        </div>
                    ) : (
                        <div className="absolute inset-0 flex items-center justify-center overflow-hidden">
                            <Image
                                src={preview}
                                alt="Preview"
                                fill
                                className="object-contain max-w-full max-h-full"
                            />

                            {/* Success Checkmark */}
                            {serverUrl && (
                                <div className="absolute inset-0 bg-black/40 flex items-center justify-center">
                                    <CheckCircle2 className="h-8 w-8 text-green-400" /> {/* Smaller checkmark */}
                                </div>
                            )}

                            {/* Remove Button */}
                            {!serverUrl && (
                                <Button
                                    variant="ghost"
                                    size="sm"
                                    className="absolute top-1 right-1 h-6 w-6 rounded-full bg-background hover:bg-destructive/20 hover:text-destructive"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        removeFile();
                                    }}
                                >
                                    <X className="h-3 w-3" /> {/* Smaller icon */}
                                </Button>
                            )}
                        </div>
                    )}
                </div>
            </div>

            {preview && !serverUrl && (
                <div className="mt-4 space-y-2"> {/* Reduced spacing */}
                    <div className="space-y-1">
                        {error ? (
                            <div className="flex items-center gap-1 text-destructive">
                                <AlertCircle className="h-3 w-3" /> {/* Smaller icon */}
                                <p className="text-xs">{error}</p> {/* Smaller text */}
                            </div>
                        ) : (
                            <Button
                                onClick={handleUpload}
                                disabled={isUploading}
                                className="w-full h-8" // Smaller button
                                size="sm" // Small size
                            >
                                {isUploading ? (
                                    <>
                                        <Loader2 className="mr-2 h-3 w-3 animate-spin" /> {/* Smaller spinner */}
                                        <span className="text-xs">Uploading...</span> {/* Smaller text */}
                                    </>
                                ) : (
                                    <span className="text-xs">Upload Image</span>
                                )}
                            </Button>
                        )}
                    </div>
                </div>
            )}
        </Card>
    );
}
