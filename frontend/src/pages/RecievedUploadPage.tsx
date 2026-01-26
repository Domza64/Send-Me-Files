import { useParams } from "react-router-dom";
import useSWR from "swr";
import fetcher from "../api/fetcher";
import ErrorMessage from "../components/ErrorMessage";

export default function ReceivedUpload() {
  const { id } = useParams();
  const { data, error, isLoading } = useSWR<String[]>(
    "http://localhost:8080/api/user/received/" + id,
    fetcher,
  );

  if (isLoading) {
    return (
      <main className="flex justify-center items-center flex-grow flex-col p-4">
        <p className="text-indigo-500 text-lg animate-pulse">
          Loading uploads...
        </p>
      </main>
    );
  }

  if (error) {
    return (
      <main className="flex justify-center items-center flex-grow flex-col p-4">
        <ErrorMessage
          error={(error as Error).message || "Something went wrong."}
        />
      </main>
    );
  }

  if (!data || data.length === 0) {
    return (
      <main className="flex justify-center items-center flex-grow flex-col p-4">
        <p className="text-center text-indigo-500 text-lg">Such empty :/</p>
      </main>
    );
  }

  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-3xl grid gap-4 mt-4">
        <p className="underline">Download all as zip</p>
        {data.map((filename) => (
          <span className="bg-indigo-950/10 border-dashed border-2 border-indigo-900 rounded-lg p-4 flex flex-col gap-2 hover:bg-indigo-950/20 transition-colors">
            {filename}
          </span>
        ))}
      </div>
    </main>
  );
}
