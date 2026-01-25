import { Link } from "react-router-dom";
import { useUserContext } from "../context/UserContext";

export default function Header() {
  const { logout, userData, isLoading } = useUserContext();
  return (
    <header className="max-w-3xl w-full mx-auto my-4 px-4">
      <nav>
        <ul className="flex justify-between font-semibold">
          <div className="flex gap-4">
            <li>
              <Link to="/">Send Me Files</Link>
            </li>
            <li>
              <Link to="/about">About</Link>
            </li>
          </div>
          {isLoading ? (
            <li>Loading...</li>
          ) : userData ? (
            <div className="flex gap-4">
              <li>
                <Link to="/recieved">Recieved</Link>
              </li>
              <li>
                <button
                  onClick={() => {
                    logout();
                  }}
                >
                  Logout
                </button>
              </li>
            </div>
          ) : (
            <li>
              <Link to="/login">Login</Link>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
}
