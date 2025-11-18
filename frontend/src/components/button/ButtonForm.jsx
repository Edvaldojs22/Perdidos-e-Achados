import style from "./ButtojnForm.module.css";

const ButtonForm = ({ text, imgUrl, typeBtn, hanlde }) => {
  return (
    <div className={style.box_buttonForm}>
      <button onClick={hanlde} className={style.bnt_submit} type={typeBtn}>
        {text}
      </button>
      <img className={style.sherdogForm} src={imgUrl} alt="" />
    </div>
  );
};

export default ButtonForm;
