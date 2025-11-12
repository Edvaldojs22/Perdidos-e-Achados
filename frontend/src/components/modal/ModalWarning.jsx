import style from "./stylesModal/ModalWarning.module.css";
import { useNavigate } from "react-router-dom";
import { MdCancel } from "react-icons/md";
import { useState } from "react";
const ModalWarning = ({ imgUrl, text, icon = true }) => {
  const [active, setActive] = useState(true);
  const navigate = useNavigate();
  return (
    <div className={active ? style.box_warning : style.box_none}>
      <div>
        <img src={imgUrl} alt="" />
        <p>{text}</p>
        <div className={style.box_btns}>
          <button type="button" onClick={() => navigate("/login")}>
            Login
          </button>
          <button type="button" onClick={() => navigate("/registrar")}>
            Cadastrar
          </button>
        </div>
        {icon && (
          <MdCancel
            className={style.mdCancel}
            onClick={() => setActive(false)}
          />
        )}
      </div>
    </div>
  );
};

export default ModalWarning;
