import { useEffect, useState } from "react";
import ModalWarning from "../modal/ModalWarning";
import { images } from "../../assets";
import { Outlet } from "react-router-dom";

const PrivateRoute = () => {
  const token = localStorage.getItem("token");
  const [mostrarAviso, setMostrarAviso] = useState(false);

  useEffect(() => {
    if (!token) {
      setMostrarAviso(true);
    }
  }, [token]);

  if (token) return <Outlet />;

  if (mostrarAviso) {
    return (
      <ModalWarning
        icon={false}
        imgUrl={images.sherdogError}
        text={
          "Por questões de segurança, é necessário fazer login ou se cadastrar para acessar esta página."
        }
      />
    );
  }

  return null;
};

export default PrivateRoute;
