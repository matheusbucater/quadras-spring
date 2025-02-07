interface EmailTemplateProps {
    name: string;
    purposeMessage: string;
    actionLink: string;
    footerMessage?: string;
}

export const EmailTemplate = ({
                                  name,
                                  purposeMessage,
                                  actionLink,
                                  footerMessage
                              }: EmailTemplateProps) => {
    return (
        <div className="bg-white p-6 rounded-lg max-w-2xl mx-auto">
            <h1 className="text-2xl font-bold mb-4">Olá {name}!</h1>
            <p className="text-gray-600 mb-6">{purposeMessage}</p>
            <a
                href={actionLink}
                className="inline-block px-6 py-3 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors"
            >
                Clique aqui.
            </a>
            <p className="mt-6 text-sm text-gray-500">
                {footerMessage}
            </p>
        </div>
    );
};