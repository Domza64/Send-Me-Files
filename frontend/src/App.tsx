import { BrowserRouter as Router, Routes, Route } from "react-router";
import Home from "./pages/HomePage";
import About from "./pages/AboutPage";
import NotFound from "./pages/NotFound";
import Header from "./components/Header";
import Login from "./pages/LoginPage";
import { UserProvider } from "./context/UserContext";
import RecievedUploads from "./pages/RecievedUploadsPage";

function App() {
  return (
    <UserProvider>
      <Router>
        <Header />

        <Routes>
          <Route path="*" element={<NotFound />} />
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/about" element={<About />} />
          <Route path="/recieved" element={<RecievedUploads />} />
        </Routes>
      </Router>
    </UserProvider>
  );
}

export default App;
