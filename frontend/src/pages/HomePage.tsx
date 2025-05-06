import UploadForm from "../components/UploadForm";

function Home() {
  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-3xl space-y-8">
        <div className="text-center space-y-4 mt-10">
          <h1 className="text-5xl font-black text-indigo-500">Send Me Files</h1>
          <p className="text-lg text-gray-300 max-w-2xl mx-auto">
            Share files quickly with anyone. No login required to send, but
            receivers need an account.
          </p>
        </div>

        <UploadForm />

        <div className="grid md:grid-cols-3 gap-6 pt-6">
          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <img
              src="/assets/sender-freedom.svg"
              className="mb-2"
              alt="Upload icon"
            />
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Sender Freedom
            </h3>
            <p className="text-gray-300">
              Upload and share files immediately without creating an account
            </p>
          </div>

          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <img
              src="/assets/people-icon.svg"
              className="mb-2"
              alt="People icon"
            />
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Secure Receiving
            </h3>
            <p className="text-gray-300">
              Recipients must be registered users to access shared files
            </p>
          </div>

          <div className="bg-gray-950 p-4 rounded-lg border border-gray-800">
            <img
              src="/assets/arrows.svg"
              className="mb-2"
              alt="Two oposite arrows icon"
            />
            <h3 className="font-bold text-lg mb-1 text-indigo-500">
              Direct Delivery
            </h3>
            <p className="text-gray-300">
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
