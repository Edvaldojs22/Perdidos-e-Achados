import { Route, Routes } from "react-router-dom";
import "./styles/variables.css";
import "./App.css";
import Home from "./pages/home/Home";
import PrivateRoute from "./components/privateRoute/PrivateRoute ";
import Login from "./pages/login/Login";
import Perfil from "./pages/perfil/Perfil";

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Home />} />

        <Route element={<PrivateRoute />}>
          <Route path="/perfil" element={<Perfil />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
