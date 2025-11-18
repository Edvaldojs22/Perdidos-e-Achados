import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { images } from "../../assets";
import ItemCard from "../../components/cardItem/ItemCard";
import style from "./Home.module.css";
import { BsSearch } from "react-icons/bs";
import { todoItens } from "../../api/itemApi";
import { FaArrowCircleLeft, FaArrowCircleRight } from "react-icons/fa";
import Confirmation from "../../components/modal/Confirmation";
import { showSuccess } from "../../service/ToasTservice";
import Loading from "../../components/modal/Loading";

const Home = () => {
  const [itens, setItens] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null);
  const [filtroNome, setFiltroNome] = useState("");
  const [filtraSetor, setFiltraSetor] = useState("");
  const [menuAberto, setMenuAberto] = useState(false);
  const [active, setActive] = useState(false);

  const navigate = useNavigate();

  const itensFiltrados = itens.filter((item) => {
    const nomeMatch = item.nome
      .toLowerCase()
      .includes(filtroNome.toLowerCase());

    const setorMatch = item.setor
      .toLowerCase()
      .includes(filtraSetor.toLowerCase());

    return nomeMatch && setorMatch;
  });

  useEffect(() => {
    const fetchItens = async () => {
      const timeout = setTimeout(() => {
        setActive(true);
      }, 8000);
      try {
        const response = await todoItens();
        clearTimeout(timeout);
        showSuccess("Dados caregados");
        setItens(response.data);
      } catch (err) {
        clearInterval(timeout);
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
      <h2>Itens Perdidos</h2>

      <select
        className={style.select_setor}
        name=""
        id=""
        placeholder="setor"
        value={filtraSetor}
        onChange={(e) => setFiltraSetor(e.target.value)}
      >
        <option value="">Setor</option>
        <option value="SETO1">SETO1</option>
        <option value="SETO2">SETO2</option>
        <option value="SETO3">SETO3</option>
        <option value="SETO4">SETO4</option>
        <option value="SETO5">SETO5</option>
      </select>

      <section className={style.boxScroll}>
        <div className={style.boxItens}>
          {loading ? (
            <Loading
              img={images.sherdogSmell}
              visible={loading}
              text={"Procurando"}
            />
          ) : erro ? (
            <p className={style.erro}>{erro}</p>
          ) : itensFiltrados.length === 0 ? (
            <p className={style.vazio}>Nenhum item encontrado.</p>
          ) : (
            itensFiltrados.map((item, index) => (
              <ItemCard
                key={index}
                nome={item.nome}
                imageUrl={item.imagemUrl}
                setor={item.setor}
                status={item.status}
                itemId={item.id}
                recompensa={item.recompensa}
                pagina={`/item/${item.id}`}
              />
            ))
          )}
        </div>
      </section>

      <div className={`${style.boxMenu} ${menuAberto ? style.ativo : ""}`}>
        <p className={style.item1} onClick={() => navigate("/novo-Item")}>
          Novo item
        </p>
        <p className={style.item2} onClick={() => navigate("/perfil")}>
          Perfil
        </p>
      </div>

      {active && (
        <Confirmation
          text={
            "⚠️ O servidor está iniciando…   Peço desculpas pela demora! Como estou usando o plano gratuito do Render, o backend pode levar até 3 minutos para ficar disponível.Por isso, os dados podem demorar um pouco para aparecer — obrigado pela paciência!"
          }
          onCancel={() => setActive(false)}
          textBtn1={""}
          textBtn2={"Aguardar"}
        />
      )}

      <div
        className={style.iconMenu}
        onClick={() => setMenuAberto(!menuAberto)}
      >
        {menuAberto ? <FaArrowCircleRight /> : <FaArrowCircleLeft />}
      </div>
    </main>
  );
};

export default Home;
