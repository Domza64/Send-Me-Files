import { useNavigate } from "react-router-dom";
import { useUserContext } from "../context/UserContext";

function RecievedUploads() {
  // TODO: recieved uploads should not be on userData, just send request to get them directly
  const { userData, isLoading, loggedIn } = useUserContext();
  const navigate = useNavigate();

  if (isLoading) {
    return <span>loading...</span>;
  }

  if (!loggedIn) {
    navigate("/login");
  }

  const empty = userData?.recievedUploads.length === 0;

  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-2xl space-y-8">
        {empty ? (
          <p className="text-center">Such empty :/</p>
        ) : (
          userData?.recievedUploads.map((upload) => (
            <div
              key={upload.uploadId}
              className="bg-indigo-950/20 border-dashed border-2 border-indigo-900 rounded-lg flex justify-between items-center overflow-hidden"
            >
              <span className="ml-3 text-indigo-500/80 overflow-hidden text-nowrap">
                {upload.title}
              </span>
              <button className="hover:text-indigo-400 text-indigo-500 text-xl transition-colors py-3 px-3 flex items-center gap-3">
                {upload.uploadId}
              </button>
            </div>
          ))
        )}
      </div>
    </main>
  );
}

export default RecievedUploads;
