import { FaGithub } from "react-icons/fa";

function About() {
  return (
    <main className="flex justify-center items-center flex-grow flex-col p-4">
      <div className="w-full max-w-2xl space-y-8">
        <div className="text-left space-y-4 mt-10">
          <h1 className="text-5xl font-black text-indigo-500">
            About Send Me Files
          </h1>
          <p className="text-lg text-gray-300 max-w-2xl mx-auto">
            A personal project build for fun and ofcourse file sharing :)
          </p>
        </div>
        <h2 className="text-2xl font-bold text-indigo-400 pt-4">Source Code</h2>
        <p className="text-gray-300">
          The project is open source and available on GitHub. Feel free to
          explore, contribute, or fork it for your own needs.
        </p>
        <a
          href="https://github.com/Domza64/Send-Me-Files"
          className="inline-flex items-center text-indigo-400 hover:text-indigo-500 mt-2"
          target="_blank"
        >
          <FaGithub className="mr-2 text-xl" />
          View on GitHub
        </a>
      </div>
    </main>
  );
}

export default About;
