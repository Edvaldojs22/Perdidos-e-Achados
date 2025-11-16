import style from "./stylesModal/Confirmation.module.css";

const Confirmation = ({ text, handle, onCancel, textBtn1, textBtn2 }) => {
  return (
    <div className={style.box_confirmation}>
      <div>
        <p>{text}</p>
        <div>
          <button type="button" onClick={handle}>
            {textBtn1 || "Comfirmar"}
          </button>
          <button onClick={onCancel}>{textBtn2 || "Cancelar"} </button>
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
