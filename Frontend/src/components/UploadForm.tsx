import { useState, useCallback, useRef, useEffect } from "react";
import ErrorMessage from "./ErrorMessage";
import { CgRemove } from "react-icons/cg";
import { useNavigate } from "react-router-dom";
import { API_URL } from "../config";

function UploadForm({ sendTo }: { sendTo?: string }) {
  const navigate = useNavigate();
  // TODO - Check if uploadID exists in URL, in that case don't ask for recpients username, also include username or uploadID in form submition.
  const dragCounter = useRef(0);
  const [files, setFiles] = useState<File[]>([]);
  const [isDragging, setIsDragging] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successfullyUploaded, setSuccessfullyUploaded] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [recipient, setRecipient] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (sendTo) {
      setRecipient(sendTo);
    }
  }, [sendTo]);

  const addFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFiles([...files, ...Array.from(e.target.files)]);
    }
  };

  const handleDragEnter = useCallback((e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    dragCounter.current++;
    setIsDragging(true);
  }, []);

  const handleDragLeave = useCallback((e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
    dragCounter.current--;
    if (dragCounter.current === 0) {
      setIsDragging(false);
    }
  }, []);

  const handleDragOver = useCallback((e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    e.stopPropagation();
  }, []);

  const handleDrop = useCallback(
    (e: React.DragEvent<HTMLDivElement>) => {
      e.preventDefault();
      e.stopPropagation();
      setIsDragging(false);

      if (e.dataTransfer.files && e.dataTransfer.files.length > 0) {
        setFiles([...files, ...Array.from(e.dataTransfer.files)]);
        dragCounter.current = 0;
        e.dataTransfer.clearData();
      }
    },
    [files],
  );

  useEffect(() => {
    setError(null);
  }, [files, recipient]);

  const removeFile = (index: number) => {
    const newFiles = [...files];
    newFiles.splice(index, 1);
    setFiles(newFiles);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    setUploading(true);
    e.preventDefault();

    const formData = new FormData();

    files.forEach((file) => {
      formData.append("files", file);
    });

    formData.append("recipient", recipient);
    formData.append("message", message);

    const host = API_URL;
    const endpoint = "/upload";
    fetch(host + endpoint, {
      method: "POST",
      body: formData,
      mode: "cors",
    })
      .then(async (response) => {
        if (response.ok) {
          setSuccessfullyUploaded(true);
          setFiles([]);
          setRecipient("");
          navigate("/");
        } else {
          const errorMessage = await response.text();
          throw new Error(errorMessage);
        }
      })
      .catch((error) => {
        setError(error.message || "Upload failed");
      })
      .finally(() => {
        setUploading(false);
      });
  };

  if (successfullyUploaded) {
    return (
      <div className="border-2 bg-indigo-950/50 border-dashed rounded-lg p-10 text-center text-indigo-200 border-indigo-700">
        <p>Files have been successfully uploaded!</p>
        <button
          className="underline mt-4 hover:text-indigo-400 transition-colors"
          onClick={() => setSuccessfullyUploaded(false)}
        >
          Upload more
        </button>
      </div>
    );
  }

  return (
    <form onSubmit={handleSubmit}>
      <div
        className={`border-2 bg-indigo-950/20 border-dashed rounded-lg p-10 text-gray-200/90 text-center cursor-pointer transition-all ${
          isDragging ? "border-indigo-500" : "border-indigo-900"
        }`}
        onDragEnter={handleDragEnter}
        onDragLeave={handleDragLeave}
        onDragOver={handleDragOver}
        onDrop={handleDrop}
      >
        <p className="mb-2">Drag and drop files here</p>
        <p className="mb-4">or</p>
        <label className="inline-block px-4 py-2 bg-indigo-600 font-medium rounded-full cursor-pointer hover:bg-indigo-700 transition-colors">
          Browse Files
          <input type="file" multiple onChange={addFile} className="hidden" />
        </label>
      </div>

      {!sendTo && (
        <div className="mt-4">
          <label
            htmlFor="recipient"
            className="block text-sm font-medium text-gray-300 mb-1"
          >
            Send to (username)
          </label>
          <input
            id="recipient"
            type="text"
            placeholder="Enter recipient's username"
            className="w-full px-3 py-2
            text-gray-200 placeholder-gray-400 bg-indigo-950/20
            focus:outline-none focus:border-indigo-500 border-dashed border-2 border-indigo-900 rounded-lg"
            required
            value={recipient}
            onChange={(e) => setRecipient(e.target.value)}
          />
        </div>
      )}

      <div className="mt-4">
        <label
          htmlFor="recipient"
          className="block text-sm font-medium text-gray-300 mb-1"
        >
          Message
        </label>
        <input
          id="recipient"
          type="text"
          placeholder="Enter a message for recipient"
          className="w-full px-3 py-2
          text-gray-200 placeholder-gray-400 bg-indigo-950/20
          focus:outline-none focus:border-indigo-500 border-dashed border-2 border-indigo-900 rounded-lg"
          required
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
      </div>

      {error && (
        <div className="mt-4">
          <ErrorMessage error={error} />
        </div>
      )}

      {files.length > 0 && (
        <div className="mt-5 text-gray-200">
          <h3 className="font-medium mb-2">Selected Files:</h3>
          <ul className="space-y-2">
            {files.map((file, index) => (
              <li
                key={index}
                className="flex justify-between items-center py-2 border-b border-gray-700"
              >
                <span>
                  {file.name}{" "}
                  <span className="text-gray-500">
                    ({Math.round(file.size / 1024)} KB)
                  </span>
                </span>
                <button
                  type="button"
                  onClick={() => removeFile(index)}
                  className="text-rose-500 hover:text-rose-700 text-xl"
                >
                  <CgRemove />
                </button>
              </li>
            ))}
          </ul>
          <div className="flex items-center mt-4">
            <button
              type="submit"
              disabled={uploading}
              className={`${uploading ? "animate-pulse" : ""} px-4 py-2 bg-indigo-600 font-medium rounded-md hover:bg-indigo-700 transition-colors disabled:bg-slate-600`}
            >
              {uploading ? "Uploading..." : "Send Files"}
            </button>
            {sendTo && (
              <div className="ml-4">
                Sending files to:{" "}
                <span className="text-lg font-semibold text-indigo-500">
                  {sendTo}
                </span>
              </div>
            )}
          </div>
        </div>
      )}
    </form>
  );
}

export default UploadForm;
