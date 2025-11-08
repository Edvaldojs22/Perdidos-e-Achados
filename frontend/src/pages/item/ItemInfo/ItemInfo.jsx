import { useParams } from "react-router-dom";
import { images } from "../../../assets";
import ButtonForm from "../../../components/button/ButtonForm";
import style from "./ItemInfo.module.css";
import { useEffect, useState } from "react";
import { itemInfo } from "../../../api/itemApi";

const ItemInfo = () => {
  const [item, setItem] = useState("");
  const { itemId } = useParams();

  useEffect(() => {
    const fetchItem = async () => {
      try {
        const response = await itemInfo(itemId);
        setItem(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchItem();
  }, []);

  return (
    <section className={style.box_itemInfo}>
      <div>
        <h2>{item.nome}</h2>

        <div className={style.box_imgs}>
          <img src={images.sherdogSeacrch} alt="dog info" />
          <img src={item.imagemUrl} alt="" />
        </div>
        <p className={style.status}>{item.status}</p>

        <div className={style.box_dados}>
          <div>
            <p>Pista Descrição:</p>
            {item.descricao ? <p>{item.descricao}</p> : <p>Sem Descriçao</p>}
          </div>
          <div>
            <p>Pista Categoria:</p>
            {item.categoria ? <p>{item.categoria}</p> : <p>Sem Categoria</p>}
          </div>
          <div>
            <p>Pista Setor:</p>
            <p>{item.setor}</p>
          </div>
          <div>
            <p>Pista Local Referência:</p>
            {item.localRef ? <p>{item.localRef}</p> : <p>Sem Referência</p>}
          </div>
          <div>
            <p>Recompença:</p>
            {item.recompensa ? (
              <p className={style.recompensa}>R${item.recompensa.toFixed(2)}</p>
            ) : (
              <p>Sem recompensa</p>
            )}
          </div>
        </div>
      </div>

      <ButtonForm
        typeBtn={"button"}
        text={"Entrar em contato"}
        imgUrl={images.sherdogWhats}
      />
    </section>
  );
};

export default ItemInfo;
