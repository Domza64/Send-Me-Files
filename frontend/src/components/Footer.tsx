import { Link } from "react-router-dom";

export default function Footer() {
  return (
    <footer className="text-center text-gray-400 text-sm py-6">
      <p>
        Made with ❤️ by{" "}
        <Link to={"https://domza.xyz"} target="_blank">
          Domza64
        </Link>
      </p>
    </footer>
  );
}
