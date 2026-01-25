import RequestFiles from "../components/RequestFiles";
import UploadForm from "../components/UploadForm";
import { IoSend } from "react-icons/io5";
import { useUserContext } from "../context/UserContext";
import { Link, useSearchParams } from "react-router-dom";

function Home() {
  const { userData, isLoading } = useUserContext();

  const [searchParams] = useSearchParams();
  const sendTo = searchParams.get("sendTo");

  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-3xl space-y-12">
        <div className="flex items-center justify-center flex-col space-y-4 mt-10 pb-10">
          <h1 className="text-5xl font-black text-indigo-500 flex items-center gap-3">
            Send Me Files
            <IoSend className="rotate-12" />
          </h1>
        </div>

        {!isLoading && userData ? (
          <div>
            <h2 className="pb-4 text-indigo-500 font-semibold text-xl">
              Request files
            </h2>
            <RequestFiles />
          </div>
        ) : (
          <div className="bg-indigo-950/20 border-dashed border-2 border-indigo-900 rounded-lg text-center">
            <h2 className="text-indigo-500 font-semibold text-xl p-2">
              Please{" "}
              <Link to={"/login"} className="underline">
                Login
              </Link>{" "}
              to Request files
            </h2>
          </div>
        )}

        <div>
          <h2 className="pb-4 text-indigo-500 font-semibold text-xl">
            Upload files
          </h2>
          <UploadForm sendTo={sendTo || undefined} />
        </div>
      </div>
    </main>
  );
}

export default Home;
