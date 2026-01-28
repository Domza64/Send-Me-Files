import { Link } from "react-router-dom";

function Home() {
  return (
    <main className="text-center flex-grow flex flex-col items-center justify-center">
      <h1 className="text-3xl font-medium mb-6">
        <span className="font-bold">404</span> - Page Not Found
      </h1>
      <Link className="underline" to={"/"}>
        Go back
      </Link>
    </main>
  );
}

export default Home;
