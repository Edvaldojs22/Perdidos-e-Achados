import { useEffect, useState } from "react";
import { images } from "../../assets";
import style from "./userProfile.module.css";
import { IoAddCircle } from "react-icons/io5";
import { useNavigate } from "react-router-dom";
import { itensDoUsuario } from "../../api/itemApi";
import ItemCard from "../../components/cardItem/ItemCard";

const UserProfile = () => {
  const [itens, setItens] = useState([]);
  const navigate = useNavigate();
  const nome = localStorage.getItem("nome");

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  useEffect(() => {
    const fetchItens = async () => {
      try {
        const response = await itensDoUsuario();
        console.log(response.data);
        setItens(response.data);
        console.log(itens);
      } catch (error) {
        console.log(error);
      }
    };
    fetchItens();
  }, []);

  return (
    <section className={style.profile}>
      <div className={style.box_imgText}>
        <div>
          <p>{nome}</p>
          <img src={images.sherdogNew} alt="" />
        </div>

        <button onClick={logout}>Sair</button>
      </div>

      <div className={style.box_itens}>
        <p>Meus Itens Postados</p>
        <div className={style.box_scroll}>
          {itens.length === 0 ? (
            <p>Nenhum intem encontrado</p>
          ) : (
            <div className={style.itens}>
              {itens.map((item) => (
                <ItemCard
                  imageUrl={
                    "https://i.pinimg.com/originals/41/a7/50/41a750515fcf291d6435fb8f224e5dde.jpg"
                  }
                  itemId={item.id}
                  nome={item.nome}
                  setor={item.setor}
                  recompensa={item.recompensa}
                  status={item.status}
                  key={item.id}
                  pagina={"/editar-item/"}
                />
              ))}
            </div>
          )}
        </div>
      </div>

      <IoAddCircle
        className={style.btn_new}
        onClick={() => navigate("/novo-item")}
      />
    </section>
  );
};

export default UserProfile;
