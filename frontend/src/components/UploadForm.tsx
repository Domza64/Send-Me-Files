import { useState, useCallback, useRef, useEffect } from "react";
import { uploadFiles } from "../api/upload";
import { CgRemove } from "react-icons/cg";

function UploadForm() {
  // TODO - Check if uploadID exists in URL, in that case don't ask for recpients username, also include username or uploadID in form submition.
  const dragCounter = useRef(0);
  const [files, setFiles] = useState<File[]>([]);
  const [isDragging, setIsDragging] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successfullyUploaded, setSuccessfullyUploaded] = useState(false);
  const [uploading, setUploading] = useState(false);
  const [recipient, setRecipient] = useState("");

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
    [files]
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

    // TODO - move to env file
    const host = "http://192.168.1.24:8080";
    const endpoint = "/api/upload";
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
        className={`border-2 bg-indigo-950/20 border-dashed rounded-lg p-10 text-center cursor-pointer transition-all ${
          isDragging ? "border-indigo-500" : "border-indigo-900"
        }`}
        onDragEnter={handleDragEnter}
        onDragLeave={handleDragLeave}
        onDragOver={handleDragOver}
        onDrop={handleDrop}
      >
        <p className="mb-2">Drag and drop files here</p>
        <p className="mb-4">or</p>
        <label className="inline-block px-4 py-2 bg-indigo-600 text-white rounded-md cursor-pointer hover:bg-indigo-700 transition-colors">
          Browse Files
          <input type="file" multiple onChange={addFile} className="hidden" />
        </label>
      </div>

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
          className="w-full px-3 py-2 bg-indigo-950/20 border border-gray-700 rounded-md text-gray-200 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500"
          required
          value={recipient}
          onChange={(e) => setRecipient(e.target.value)}
        />
      </div>

      {error && (
        <div className="text-rose-500 border-rose-700 bg-rose-950/60 rounded-md p-2 mt-4 border-dashed border-2">
          {error}
        </div>
      )}

      {files.length > 0 && (
        <div className="mt-5">
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
                  x
                </button>
              </li>
            ))}
          </ul>
          <button
            type="submit"
            disabled={uploading}
            className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors disabled:bg-slate-600"
          >
            {uploading ? "Uploading..." : "Upload Files"}
          </button>
        </div>
      )}
    </form>
  );
}

export default UploadForm;
