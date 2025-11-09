import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { images } from "../../assets";
import ItemCard from "../../components/cardItem/ItemCard";
import style from "./Home.module.css";
import { BsSearch } from "react-icons/bs";
import { buscaTodoItens } from "../../api/itemApi";

const Home = () => {
  const [itens, setItens] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null);
  const [filtroNome, setFiltroNome] = useState("");

  const navigate = useNavigate();

  const itensFiltrados = itens.filter((item) => {
    const nomeMatch = item.nome
      .toLowerCase()
      .includes(filtroNome.toLowerCase());

    return nomeMatch;
  });

  useEffect(() => {
    const fetchItens = async () => {
      try {
        const response = await buscaTodoItens();
        setItens(response.data);
      } catch (err) {
        console.error("Erro ao buscar itens:", err);
        setErro("Não foi possível carregar os itens.");
      } finally {
        setLoading(false);
      }
    };

    fetchItens();
  }, []);

  return (
    <main>
      <header>
        <h1>Perdidos e Achados</h1>
        <div className={style.box_input}>
          <BsSearch className={style.icon_search} />
          <input
            type="text"
            placeholder="Nome"
            value={filtroNome}
            onChange={(e) => setFiltroNome(e.target.value)}
          />
          <img
            className={style.sherdogSmell}
            src={images.sherdogSmell}
            alt=""
          />
          <img className={style.sherdog} src={images.sherdog} alt="" />
        </div>
      </header>
      <h2>Items Perdidos</h2>

      <section className={style.boxScroll}>
        <div className={style.boxItens}>
          {loading ? (
            <p>Procurando itens...</p>
          ) : erro ? (
            <p className={style.erro}>{erro}</p>
          ) : itensFiltrados.length === 0 ? (
            <p className={style.vazio}>Nenhum item encontrado.</p>
          ) : (
            itensFiltrados.map((item) => (
              <ItemCard
                key={item.id}
                nome={item.nome}
                imageUrl={
                  "https://i.pinimg.com/originals/41/a7/50/41a750515fcf291d6435fb8f224e5dde.jpg"
                }
                setor={item.setor}
                status={item.status}
                itemId={item.id}
                recompensa={item.recompensa}
                pagina={"/item/"}
              />
            ))
          )}
        </div>
      </section>
      <button>Carregar</button>
      <img
        className={style.iconBox}
        src={images.box}
        alt="Novo item"
        onClick={() => navigate("/novo-item")}
      />
    </main>
  );
};

export default Home;
