import style from "./ButtojnForm.module.css";

const ButtonForm = ({ text, imgUrl, typeBtn }) => {
  return (
    <div className={style.box_buttonForm}>
      <button className={style.bnt_submit} type={typeBtn}>
        {text}
      </button>
      <img className={style.sherdogForm} src={imgUrl} alt="" />
    </div>
  );
};

export default ButtonForm;
