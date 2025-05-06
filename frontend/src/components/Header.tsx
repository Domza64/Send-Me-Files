import { Link } from "react-router-dom";

function header() {
  return (
    <header className="max-w-xs w-full mx-auto my-4">
      <nav>
        <ul className="flex justify-between font-semibold">
          <li>
            <Link to="/">Send Me Files</Link>
          </li>
          <li>
            <Link to="/about">About</Link>
          </li>
          <li>
            <Link to="/login">Login</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default header;
