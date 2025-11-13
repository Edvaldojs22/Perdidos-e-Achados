import style from "./stylesModal/Confirmation.module.css";

const Confirmation = ({ text, handleExcluir, onCancel }) => {
  return (
    <div className={style.box_confirmation}>
      <div>
        <p>{text}</p>
        <div>
          <button type="button" onClick={handleExcluir}>Confrimar</button>
          <button onClick={onCancel}>Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default Confirmation;
