import { useEffect, useState } from "react";
import { images } from "../../assets";
import style from "./UserProfile.module.css";
import { IoAddCircle } from "react-icons/io5";
import { useNavigate } from "react-router-dom";
import { itensDoUsuario } from "../../api/itemApi";
import ItemCard from "../../components/cardItem/ItemCard";
import Loading from "../../components/modal/Loading";
import { excluirUser } from "../../api/user";
import Confirmation from "../../components/modal/Confirmation";
import { showSuccess, showWarning } from "../../service/ToasTservice";

const UserProfile = () => {
  const [itens, setItens] = useState([]);
  const [active, setActive] = useState(false);
  const [textModal, setTextModal] = useState("");
  const navigate = useNavigate();
  const nome = localStorage.getItem("nome");
  const [loading, setLoading] = useState(false);

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("nome");
    localStorage.removeItem("telefone");
    navigate("/login");
  };

  useEffect(() => {
    setLoading(true);
    setTextModal("Buscando itens");
    const fetchItens = async () => {
      try {
        const response = await itensDoUsuario();
        setItens(response.data);
      } catch (error) {
        console.log(error.response.data);
      } finally {
        setLoading(false);
      }
    };
    fetchItens();
  }, []);

  const hadleExcluir = async () => {
    if (localStorage.getItem("id") === "48") {
      showWarning("Esta conta é para teste, não pode ser excluida!");
      setActive(false);
      return;
    }
    setActive(false);
    setTextModal("Excluindo");
    setLoading(true);
    try {
      await excluirUser();
      showSuccess("Usuario excluido");
      localStorage.removeItem("token");
      localStorage.removeItem("nome");
      localStorage.removeItem("telefone");
      showSuccess("Usuario excluido");
      navigate("/");
    } catch (error) {
      console.log(error.response.data);
      showWarning(error.response.data);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className={style.profile}>
      <div className={style.box_imgText}>
        <h2>Bem vindo!</h2>
        <div>
          <p>{nome}</p>
          <img src={images.sherdogNew} alt="" />
        </div>
        <button onClick={logout}>Sair</button>
        <button onClick={() => setActive(true)}>Excluir usuario</button>
      </div>

      <div className={style.box_itens}>
        <p>Meus Itens Postados</p>
        <div className={style.box_scroll}>
          {loading ? (
            <Loading
              text={textModal}
              img={images.sherdog}
              visible={loading}
              top={true}
            />
          ) : itens.length === 0 ? (
            <p>Você não tem nenhum item</p>
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

      {active && (
        <Confirmation
          text={"Deseja excluir sua conta?"}
          handle={hadleExcluir}
          textBtn1={"Comfirmar"}
          textBtn2={"Cancelar"}
          onCancel={() => setActive(false)}
        />
      )}
    </section>
  );
};

export default UserProfile;
