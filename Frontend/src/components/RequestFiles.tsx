import { useState } from "react";
import { FaRegCopy } from "react-icons/fa6";
import { useUserContext } from "../context/UserContext";

function RequestFiles() {
  const { userData } = useUserContext();
  const [copied, setCopied] = useState(false);

  const requestURL = `http://localhost:5173/?sendTo=${userData?.username}`;
  const copyToClipboard = () => {
    navigator.clipboard.writeText(requestURL);
    setCopied(true);
  };

  return (
    <div className="bg-indigo-950/20 border-dashed border-2 border-indigo-900 rounded-lg flex justify-between items-center overflow-hidden">
      <span className="ml-3 text-indigo-500/80 overflow-hidden text-nowrap">
        {requestURL}
      </span>
      <button
        onClick={copyToClipboard}
        className="hover:text-indigo-400 text-indigo-500 text-xl transition-colors py-3 px-3 flex items-center gap-3"
      >
        {copied && (
          <span className="text-sm text-indigo-500 min-w-max pl-1">
            Copied to clipboard
          </span>
        )}
        <FaRegCopy />
      </button>
    </div>
  );
}

export default RequestFiles;
