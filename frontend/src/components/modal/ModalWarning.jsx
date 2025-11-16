import style from "./stylesModal/ModalWarning.module.css";
import { useNavigate } from "react-router-dom";
import { MdCancel } from "react-icons/md";
const ModalWarning = ({ imgUrl, text }) => {
  const navigate = useNavigate();
  return (
    <div className={style.box_warning}>
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
        <MdCancel className={style.mdCancel} onClick={() => navigate(-1)} />
      </div>
    </div>
  );
};

export default ModalWarning;
