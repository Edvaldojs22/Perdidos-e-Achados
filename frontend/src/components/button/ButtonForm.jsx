import style from "./ButtojnForm.module.css";

const ButtonForm = ({ text, imgUrl }) => {
  return (
    <div className={style.box_buttonForm}>
      <button className={style.bnt_submit} type="submit">
        {text}
      </button>
      <img className={style.sherdogForm} src={imgUrl} alt="" />
    </div>
  );
};

export default ButtonForm;
