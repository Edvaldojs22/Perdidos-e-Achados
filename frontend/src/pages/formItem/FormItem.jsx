import style from "./FormItem.module.css";
import { images } from "../../assets";
import { useEffect, useRef, useState } from "react";
import { createItem } from "../../api/itemApi";
import { MdCancel } from "react-icons/md";
import ModalWarning from "../../components/modal/ModalWarning";

const FormItem = () => {
  const inputRef = useRef(null);
  const [previewImg, setPreviewImg] = useState(null);
  const [setor, setSetor] = useState("");
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [categoria, setCategoria] = useState("");
  const [localRef, setLocalRef] = useState("");
  const [telefone, setTelefone] = useState("");
  const [mostrarAviso, setMostrarAviso] = useState(false);

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

    if (!imagem) {
      alert("Selecione uma imagem");
      return;
    }

    // Cria o objeto com os dados

    const dados = {
      nome,
      descricao,
      categoria,
      setor,
      localRef,
      contato: telefone,
    };

    // Cria o FormData
    const formData = new FormData();
    formData.append(
      "dados",
      new Blob([JSON.stringify(dados)], { type: "application/json" })
    );

    formData.append("imagem", imagem); // arquivo

    try {
      const response = await createItem(formData);
      console.log("Item criado:", response);
    } catch (err) {
      if (err?.response) {
        console.log("Erro ao cadastrar:", err.response.data);
      } else {
        console.log("Erro inesperado:", err.message || err);
      }
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) setMostrarAviso(true);
  }, []);

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
          required
        />
      </div>

      <section>
        <h2>Preencha tudo para uma boa busca!</h2>
        <div>
          <label htmlFor="pistaNome">Pista Nome:</label>
          <input
            id="pistaNome"
            name="pistaNome"
            type="text"
            placeholder="Ex: Cateira"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="pistaDescrição">Pista Descrição:</label>
          <input
            id="pistaDescrição"
            name="pistaDescrição"
            type="text"
            required
            placeholder="Ex: Cor, Marca, Tamanho"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="pistaCategoria">Pista Categori:</label>
          <select
            name="pistaCategoria"
            id=""
            value={categoria}
            onChange={(e) => setCategoria(e.target.value)}
          >
            <option value="">Selecione uma categoria</option>
            <option value="Eletronico">Eletronico</option>
            <option value=""></option>
          </select>
        </div>
        <div>
          <label htmlFor="pistaSetor">Setor:</label>
          <select
            id="setor"
            name="setor"
            onChange={(e) => setSetor(e.target.value)}
          >
            <option value=""> Escolha um setor </option>
            <option value="checkin">Área de Check-in</option>
            <option value="embarque">Área de Embarque</option>
            <option value="desembarque">Área de Desembarque</option>
            <option value="seguranca">Área de Segurança</option>
            <option value="alimentacao">
              Praça de Alimentação / Restaurantes
            </option>
            <option value="lojas">Lojas / Duty Free</option>
            <option value="banheiros">Banheiros</option>
            <option value="estacionamento">Estacionamento</option>
            <option value="entrada">Área Externa / Entrada do Aeroporto</option>
            <option value="salas_vip">Salas VIP / Salas de Espera</option>
            <option value="achados_perdidos">Achados e Perdidos</option>
          </select>
        </div>

        <div>
          <label htmlFor="pistaReferencia">Pista Referência:</label>
          <input
            name="pistaLocalReferencia"
            type="text"
            required
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
            required
            placeholder="Ex: (83) 9XXXX-XXXX"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
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
      <div>
        <button className={style.bnt_submit} type="submit">
          Postar
        </button>
        <img className={style.sherdogForm} src={images.sherdogForm} alt="" />
      </div>
    </form>
  );
};

export default FormItem;
