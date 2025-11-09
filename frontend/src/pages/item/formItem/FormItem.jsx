import style from "./FormItem.module.css";
import { images } from "../../../assets";
import { useEffect, useRef, useState } from "react";
import { createItem } from "../../../api/itemApi";
import { MdCancel } from "react-icons/md";
import ModalWarning from "../../../components/modal/ModalWarning";
import ButtonForm from "../../../components/button/ButtonForm";
import { showSuccess } from "../../../service/ToasTservice";

const FormItem = ({ modo = "criar", item = null }) => {
  const isEdicao = modo === "editar";
  const inputRef = useRef(null);
  const [previewImg, setPreviewImg] = useState(null);
  const [setor, setSetor] = useState(null);
  const [nome, setNome] = useState(null);
  const [descricao, setDescricao] = useState(null);
  const [categoria, setCategoria] = useState(null);
  const [localRef, setLocalRef] = useState(null);
  const [telefone, setTelefone] = useState(null);
  const [recompensa, setRecompensa] = useState(null);
  const [mostrarAviso, setMostrarAviso] = useState(false);
  const [erros, setErros] = useState({});

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleCleanImg = () => {
    setPreviewImg(null);
  };

  const handleChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const imageURL = URL.createObjectURL(file);
      setPreviewImg(imageURL);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const imagem = inputRef.current.files[0];

    if (!isEdicao && !imagem) {
      alert("Selecione uma imagem");
      return;
    }

    const dados = {
      nome,
      descricao,
      categoria,
      setor,
      localRef,
      contato: telefone,
      recompensa,
    };

    // Cria o FormData
    const formData = new FormData();
    formData.append(
      "dados",
      new Blob([JSON.stringify(dados)], { type: "application/json" })
    );

    // Só adiciona imagem se estiver criando ou trocando
    if (!isEdicao || imagem) {
      formData.append("imagem", imagem);
    }

    try {
      if (isEdicao) {
        console.log("Edicao abilitada");
      } else {
        if (!imagem) {
          alert("Selecione uma imagem");
        }
        await createItem(formData);
        showSuccess("Item postado com Sucesso!");
      }

      setNome("");
      setDescricao("");
      setCategoria("");
      setSetor("");
      setLocalRef("");
      setTelefone("");
      setRecompensa("");
      setPreviewImg(null);
      inputRef.current.value = null;
    } catch (err) {
      if (err?.response) {
        console.log("Erro ao cadastrar:", err.response.data);
        setErros(err.response.data);
        if (
          err.response.data.mensagem ==
          "Esta rota requer um Token JWT válido (Bearer Token)."
        ) {
          setMostrarAviso(false);
        }
      } else {
        console.log("Erro inesperado:", err.message || err);
      }
    }
  };

  useEffect(() => {
    if (isEdicao && item) {
      setNome(item.nome || "");
      setDescricao(item.descricao || "");
      setCategoria(item.categoria || "");
      setSetor(item.setor || "");
      setLocalRef(item.localRef || "");
      setTelefone(item.contato || "");
      setRecompensa(item.recompensa || "");
      setPreviewImg(item.imagemUrl || null);
    }
  }, [item, isEdicao]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) setMostrarAviso(true);
  }, []);

  if (isEdicao && !item) {
    return <p>Carregando item para edição...</p>;
  }

  return (
    <form onSubmit={handleSubmit} className={style.formItem} action="">
      <div onClick={handleClick}>
        {previewImg ? (
          <div>
            <img
              className={style.imagemPreview}
              src={previewImg}
              alt="Prévia"
            />
            <MdCancel onClick={handleCleanImg} className={style.mdCancel} />
          </div>
        ) : (
          <div className={style.box_iconText}>
            <img
              className={style.sherdogSelf}
              src={images.sherdogSelf}
              alt="Ícone"
            />
            <p>Carregar imagem</p>
          </div>
        )}

        <input
          type="file"
          name="imagem"
          id="imagem"
          ref={inputRef}
          accept="image/*"
          onChange={handleChange}
          required={!isEdicao}
        />
      </div>

      <section>
        <h2>
          {isEdicao ? "Edite seu item" : "Preencha tudo para uma boa busca!"}
        </h2>

        <div>
          <label htmlFor="pistaNome">Pista Nome:</label>
          <input
            id="pistaNome"
            name="pistaNome"
            type="text"
            placeholder={erros.nome ? `${erros.nome}!` : "Ex: Carteira"}
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required={!isEdicao}
          />
        </div>
        <div>
          <label htmlFor="pistaDescrição">Pista Descrição:</label>
          <input
            id="pistaDescrição"
            name="pistaDescrição"
            type="text"
            placeholder="Ex: Cor, Marca, Tamanho"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="pistaCategoria">Pista Categoria:</label>
          <select
            name="pistaCategoria"
            id=""
            value={categoria}
            onChange={(e) => setCategoria(e.target.value)}
            required={!isEdicao}
          >
            <option value="">Selecione uma categoria</option>
            <option value="DOCUMENTO">Documento</option>
            <option value="ELETRONICO">Eletronico</option>
            <option value="BAGAGEM">Bagagem</option>
            <option value="ROUPA">Roupa</option>
            <option value="DINHEIRO">Dinheiro</option>
            <option value="OBJETOS_PESSOAL">Objeto Pessoal</option>
            <option value="BRINQUEDO">Brinquedo</option>
            <option value="MEDICAMENTO">Medicamento</option>
            <option value="JOIA">Joia</option>
            <option value="OUTRO">Outro</option>
          </select>
        </div>
        <div>
          <label htmlFor="pistaSetor">Setor:</label>
          <select
            id="setor"
            name="setor"
            onChange={(e) => setSetor(e.target.value)}
            required={!isEdicao}
            value={setor}
          >
            <option value=""> Escolha um setor </option>
            <option value="SETO1">SETO1</option>
            <option value="SETO2">SETO2</option>
            <option value="SETO3">SETO3</option>
            <option value="SETO4">SETO4</option>
            <option value="SETO5">SETO5</option>
          </select>
        </div>

        <div>
          <label htmlFor="pistaReferencia">Pista Referência:</label>
          <input
            name="pistaLocalReferencia"
            type="text"
            placeholder="Ex: Perto da escada"
            value={localRef}
            onChange={(e) => setLocalRef(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="pistaTelefone">Pista Telefone:</label>
          <input
            name="pistaTelefone"
            type="text"
            placeholder={
              erros.contato
                ? "Formato esperado (83) 9XXXX-XXXX"
                : "Ex: (83) 9XXXX-XXXX"
            }
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            required={!isEdicao}
          />
        </div>
        <div>
          <label htmlFor="recompensa">Recompensa</label>
          <input
            name="recompensa"
            type="number"
            min={1}
            value={recompensa}
            placeholder="Ex: 100"
            onChange={(e) => setRecompensa(e.target.value)}
          />
        </div>
      </section>
      {mostrarAviso && (
        <ModalWarning
          text={
            "Faça login ou cadastre-se para conseguir postar um item. Assim garantimos que todos os itens sejam publicados com segurança e responsabilidade"
          }
          imgUrl={images.sherdogError}
        />
      )}

      <ButtonForm
        text={isEdicao ? "Editar" : "Postar"}
        imgUrl={images.sherdogForm}
      />
    </form>
  );
};

export default FormItem;
