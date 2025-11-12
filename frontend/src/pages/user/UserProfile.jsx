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
        setItens(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchItens();
  }, []);

  return (
    <section className={style.profile}>
      <div className={style.box_imgText}>
        <h2>Bem vindo!</h2>
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
              {itens.map((item, index) => (
                <ItemCard
                  key={index}
                  imageUrl={item.imagemUrl}
                  itemId={item.id}
                  nome={item.nome}
                  setor={item.setor}
                  recompensa={item.recompensa}
                  status={item.status}
                  pagina={`/editar-item/${item.id}`}
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
