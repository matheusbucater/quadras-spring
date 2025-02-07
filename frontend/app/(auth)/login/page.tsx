import { GiTennisCourt } from "react-icons/gi";
import Link from "next/link";

import { LoginForm } from "@/components/login-form"

export default function LoginPage() {

  return (
    <div className="grid min-h-svh lg:grid-cols-2">
      <div className="flex flex-col gap-4 p-6 md:p-10">
        <div className="flex justify-center gap-2 md:justify-start">
          <Link href="/" className="flex items-center gap-2 font-medium">
            <div className="flex h-10 w-10 items-center justify-center rounded-md bg-amber-400 text-primary-foreground">
              <GiTennisCourt className="size-7 text-white" />
            </div>
            <span className="font-semibold text-xl text-zinc-600">{ "Quadras SMC" }</span>
          </Link>
        </div>
        <div className="flex flex-1 items-center justify-center">
          <div className="w-full max-w-md">
            <LoginForm />
          </div>
        </div>
      </div>
      <div className="relative hidden bg-muted lg:block">
        <img
          src="/placeholder.svg"
          alt="Image"
          className="absolute inset-0 h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
        />
      </div>
    </div>
  )
}

