import style from "./stylesModal/Confirmation.module.css";

const Confirmation = ({ text, handle, onCancel, textBtn1, textBtn2 }) => {
  return (
    <div className={style.box_confirmation}>
      <div>
        <p>{text}</p>
        <div>
          {textBtn1 && (
            <button type="button" onClick={handle}>
              {textBtn1}
            </button>
          )}
          {textBtn2 && <button onClick={onCancel}>{textBtn2} </button>}
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
