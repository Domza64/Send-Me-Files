import { BsPeopleFill } from "react-icons/bs";
import RequestFiles from "../components/RequestFiles";
import UploadForm from "../components/UploadForm";
import { HiArrowsRightLeft } from "react-icons/hi2";
import { MdOutlineFileUpload } from "react-icons/md";
import { IoSend } from "react-icons/io5";

function Home() {
  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-3xl space-y-12">
        <div className="flex items-center justify-center flex-col space-y-4 mt-10 pb-10">
          <h1 className="text-5xl font-black text-indigo-500 flex items-center gap-2">
            <IoSend />
            Send Me Files
          </h1>
          <p className="text-lg text-gray-200 max-w-2xl mx-auto pl-12">
            Share files quickly with anyone.
          </p>
        </div>

        <div>
          <h2 className="pb-4 text-indigo-500 font-semibold text-xl">
            Request files
          </h2>
          <RequestFiles />
        </div>

        <div>
          <h2 className="pb-4 text-indigo-500 font-semibold text-xl">
            Upload files
          </h2>
          <UploadForm />
        </div>

        <div className="grid md:grid-cols-3 gap-6 pt-6">
          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <div className="text-4xl text-indigo-500 mb-2">
              <MdOutlineFileUpload />
            </div>
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Sender Freedom
            </h3>
            <p className="text-gray-300/90">
              Upload and share files immediately without creating an account
            </p>
          </div>

          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <div className="text-4xl text-indigo-500 mb-2">
              <BsPeopleFill />
            </div>
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Secure Receiving
            </h3>
            <p className="text-gray-300/90">
              Recipients must be registered users to access shared files
            </p>
          </div>

          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <div className="text-4xl text-indigo-500 mb-2">
              <HiArrowsRightLeft />
            </div>
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Direct Delivery
            </h3>
            <p className="text-gray-300/90">
              Files go straight to the intended recipient's account
            </p>
          </div>
        </div>

        <div className="pt-8 text-center text-gray-400 text-sm">
          <p>
            Files are automatically removed after download • Only share with
            trusted recipients
          </p>
        </div>
      </div>
    </main>
  );
}

export default Home;
