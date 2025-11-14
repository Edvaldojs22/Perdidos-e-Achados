import style from "./stylesModal/Loading.module.css";
const Loading = ({ visible, img, text, top = false }) => {
  if (!visible) return null;
  return (
    <div
      className={`${style.box_loading} ${
        top ? style.box_loadingTop : style.box_loadingCenter
      }`}
    >
      <div>
        <img src={img} alt="" />
        <div className={style.dotContainer}>
          <p>{text}</p>
          <div className={style.dot}></div>
          <div className={style.dot}></div>
          <div className={style.dot}></div>
        </div>
      </div>
    </div>
  );
};

export default Loading;
