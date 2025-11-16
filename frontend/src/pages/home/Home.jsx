import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { images } from "../../assets";
import ItemCard from "../../components/cardItem/ItemCard";
import style from "./Home.module.css";
import { BsSearch } from "react-icons/bs";
import { todoItens } from "../../api/itemApi";
import { FaArrowCircleLeft, FaArrowCircleRight } from "react-icons/fa";
import { itensMock } from "../../mocks/itensMock";
import Confirmation from "../../components/modal/Confirmation";
import { showSuccess } from "../../service/ToasTservice";

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

  const handleSetarDadosMocados = () => {
    setItens(itensMock);
    setActive(false);
    console.log(itensMock);
  };

  useEffect(() => {
    const fetchItens = async () => {
      const timeout = setTimeout(() => {
        setActive(true);
      }, 5000);

      try {
        const response = await todoItens();
        clearTimeout(timeout);
        setActive(false);
        showSuccess("Dados caregados");
        setItens(response.data);
      } catch (err) {
        clearInterval(timeout);
        console.error("Erro ao buscar itens:", err);
        setErro("Não foi possível carregar os itens.");
        setItens(itensMock); // fallback em caso de erro
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
            <p>Carregando itens</p>
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
          text={`⚠️ O servidor pode estar reiniciando neste momento, por isso os dados podem demorar a aparecer.  
                Como estou utilizando o plano gratuito do Render, o backend pode levar até 3 minutos para ficar disponível.  
                Você prefere continuar navegando com dados de exemplo (mockados) ou aguardar os dados reais?`}
          handle={handleSetarDadosMocados}
          onCancel={() => setActive(false)}
          textBtn1={"Usar"}
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
