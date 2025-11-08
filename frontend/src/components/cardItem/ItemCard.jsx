import { useNavigate } from "react-router-dom";
import { images } from "../../assets";
import styles from "./ItemCard.module.css";

const ItemCard = ({ imageUrl, nome, cidade, postado, itemId }) => {
  const navigate = useNavigate();
  return (
    <div onClick={() => navigate(`/item/${itemId}`)} className={styles.card}>
      <div>
        <img
          className={styles.imgItem}
          src={imageUrl}
          alt="imagem do Item perdido"
        />
        <div className={styles.text}>
          <p>{nome}</p>
          <p>{cidade}</p>
        </div>
      </div>
      <p className={styles.dataPostado}>
        {new Date(postado).toLocaleDateString("pt-BR")}
      </p>
      <img className={styles.imgILupa} src={images.lupa} alt="" />
    </div>
  );
};

export default ItemCard;
