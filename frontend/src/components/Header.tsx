import { Link } from "react-router-dom";
import { useUserContext } from "../context/UserContext";

function header() {
  const { logout, userData, isLoading } = useUserContext();
  return (
    <header className="max-w-lg w-full mx-auto my-4">
      <nav>
        <ul className="flex justify-between font-semibold">
          <li>
            <Link to="/">Send Me Files</Link>
          </li>
          <li>
            <Link to="/about">About</Link>
          </li>
          {isLoading ? (
            <li>Loading...</li>
          ) : userData ? (
            <>
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
            </>
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

export default header;
