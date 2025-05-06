import { BrowserRouter as Router, Routes, Route } from "react-router";
import Home from "./pages/HomePage";
import About from "./pages/AboutPage";
import NotFound from "./pages/NotFound";
import Header from "./components/Header";

function App() {
  return (
    <Router>
      <Header />

      <Routes>
        <Route path="*" element={<NotFound />} />
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </Router>
  );
}

export default App;
