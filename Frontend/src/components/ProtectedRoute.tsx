
import { useUserContext } from "../context/UserContext";
import Login from "../pages/LoginPage";

export default function ProtectedRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const { loggedIn, isLoading } = useUserContext();

  if (isLoading) {
    return (
      <main className="flex justify-center items-center flex-grow flex-col p-4">
        <p className="text-indigo-500 text-lg animate-pulse">Loading...</p>
      </main>
    );
  }

  if (!loggedIn) {
    return <Login />;
  }

  return <>{children}</>;
}
