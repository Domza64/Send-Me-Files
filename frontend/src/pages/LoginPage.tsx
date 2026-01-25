import { useState } from "react";
import ErrorMessage from "../components/ErrorMessage";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../context/UserContext";

function Login() {
  const { login, loggedIn } = useUserContext();
  const navigate = useNavigate();

  if (loggedIn) {
    navigate("/");
  }

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    try {
      // TODO: Move to some constants or env file
      const response = await fetch(
        "http://localhost:8080/api/auth/generateToken",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: email, password }),
        },
      );

      if (!response.ok) {
        throw new Error("Invalid credentials");
      }

      const token = await response.text();
      login(token);
      navigate("/");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Login failed");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-md">
        <div className="pb-12 text-center">
          <h1 className="text-5xl font-black text-indigo-500 mb-2">
            Login to SMF
          </h1>
          <p className="text-lg text-gray-200">
            Access your file sharing account
          </p>
        </div>

        <form
          onSubmit={handleSubmit}
          className="border-2 border-dashed rounded-lg border-indigo-700 p-4"
        >
          {error && <ErrorMessage error={error} />}
          <div className="mb-6">
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-300 mb-1"
            >
              Email
            </label>
            <input
              id="email"
              type="text"
              className="w-full px-3 py-2 bg-indigo-950/20 border border-gray-700 rounded-md text-gray-200 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setError(null);
              }}
              required
            />
          </div>

          <div className="mb-6">
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-300 mb-1"
            >
              Password
            </label>
            <input
              id="password"
              type="password"
              className="w-full px-3 py-2 bg-indigo-950/20 border border-gray-700 rounded-md text-gray-200 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                setError(null);
              }}
              required
            />
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className="w-full px-4 py-2 bg-indigo-600 text-white font-medium rounded-md hover:bg-indigo-700 transition-colors disabled:bg-slate-600 flex justify-center items-center gap-2"
          >
            {isLoading ? (
              <>
                <svg
                  className="animate-spin h-5 w-5 text-white"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                  ></circle>
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  ></path>
                </svg>
                Authenticating...
              </>
            ) : (
              "Login"
            )}
          </button>
        </form>

        <div className="mt-6 text-center text-sm text-gray-400">
          <p>
            Don't have an account?{" "}
            <button
              onClick={() => setError("Registering is not yet implemented")}
              className="text-indigo-400 hover:text-indigo-300 underline transition-colors"
            >
              Sign up here
            </button>
          </p>
          <p className="mt-2">
            <button
              onClick={() => setError("Pasword reset is not yet implemented")}
              className="text-indigo-400 hover:text-indigo-300 underline transition-colors"
            >
              Forgot password?
            </button>
          </p>
        </div>
      </div>
    </main>
  );
}

export default Login;
