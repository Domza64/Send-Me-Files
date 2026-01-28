import { useParams } from "react-router-dom";
import useSWR from "swr";
import fetcher from "../api/fetcher";
import ErrorMessage from "../components/ErrorMessage";
import { useEffect, useState } from "react";
import { API_URL } from "../config";

export default function ReceivedUpload() {
  const { id } = useParams();
  const { data, error, isLoading } = useSWR<String[]>(
    "http://localhost:8080/api/user/received/" + id,
    fetcher,
  );
  const [zipping, setZipping] = useState(false);
  const [downloadReady, setDownloadReady] = useState<string | undefined>(
    undefined,
  );

  const requestDownload = () => {
    fetch(`${API_URL}/user/zip/${id}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/zip",
        Authorization: `Bearer ${sessionStorage.getItem("token")}`,
      },
    }).then((response) => {
      if (response.ok) {
        setZipping(true);
      }
    });
  };

  useEffect(() => {
    if (zipping) {
      // Todo: actually check if the zip is ready every 5 seconds... in future use websocket or something
      setTimeout(() => {
        setZipping(false);
        setDownloadReady("downloadId");
      }, 5000);
    }
  }, [zipping]);

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
        <button onClick={requestDownload} className="underline">
          Download all as zip
        </button>
        {downloadReady && (
          <span className="bg-indigo-500 p-4 rounded-lg">Download zip</span>
        )}
        {zipping && (
          <span className="bg-indigo-500 p-4 rounded-lg animate-pulse">
            Zipping...
          </span>
        )}
        {data.map((filename, index) => (
          <span
            key={index}
            className="bg-indigo-950/10 border-dashed border-2 border-indigo-900 rounded-lg p-4 flex flex-col gap-2 hover:bg-indigo-950/20 transition-colors"
          >
            {filename}
          </span>
        ))}
      </div>
    </main>
  );
}
