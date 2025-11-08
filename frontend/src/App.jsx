import { Route, Routes } from "react-router-dom";
import "./styles/variables.css";
import "./styles/toast.css";
import "./App.css";
import Home from "./pages/home/Home";
import PrivateRoute from "./components/privateRoute/PrivateRoute ";
import Login from "./pages/login/Login";
import Perfil from "./pages/perfil/Perfil";
import FormItem from "./pages/formItem/FormItem";
import RegisterUser from "./pages/formUser/RegisterUser";
import { ToastContainer } from "react-toastify";

function App() {
  return (
    <div className="app">
      <ToastContainer />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Home />} />
        <Route path="/novo-item" element={<FormItem />} />
        <Route path="/registrar" element={<RegisterUser />} />

        <Route element={<PrivateRoute />}>
          <Route path="/perfil" element={<Perfil />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
