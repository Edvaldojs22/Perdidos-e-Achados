import { useNavigate } from "react-router-dom";
import styles from "./ItemCard.module.css";

const ItemCard = ({ imageUrl, nome, setor, status, itemId, recompensa }) => {
  const navigate = useNavigate();
  return (
    <div onClick={() => navigate(`/item/${itemId}`)} className={styles.card}>
      <div>
        <div className={styles.box_imgTetx}>
          <img
            className={styles.imgItem}
            src={imageUrl}
            alt="imagem do Item perdido"
          />
          <div className={styles.box_text}>
            <p>{nome}</p>
            <p>{setor}</p>
            {recompensa && <p>Recompença: R$ {recompensa}</p>}
          </div>
        </div>
      </div>
      <p className={styles.status}>{status}</p>
    </div>
  );
};

export default ItemCard;
