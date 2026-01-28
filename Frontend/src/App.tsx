import { BrowserRouter as Router, Routes, Route } from "react-router";
import Home from "./pages/HomePage";
import About from "./pages/AboutPage";
import NotFound from "./pages/NotFound";
import Header from "./components/Header";
import Login from "./pages/LoginPage";
import { UserProvider } from "./context/UserContext";
import Footer from "./components/Footer";
import ReceivedUpload from "./pages/RecievedUploadPage";
import ReceivedUploads from "./pages/ReceivedUploadsPage";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  const env = import.meta.env.MODE;
  console.log("ENV:", env);
  return (
    <UserProvider>
      <Router>
        <Header />

        <Routes>
          <Route path="*" element={<NotFound />} />
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/about" element={<About />} />
          <Route
            path="/received"
            element={
              <ProtectedRoute>
                <ReceivedUploads />
              </ProtectedRoute>
            }
          />
          <Route
            path="/receivedUpload/:id"
            element={
              <ProtectedRoute>
                <ReceivedUpload />
              </ProtectedRoute>
            }
          />
        </Routes>

        <Footer />
      </Router>
    </UserProvider>
  );
}

export default App;
