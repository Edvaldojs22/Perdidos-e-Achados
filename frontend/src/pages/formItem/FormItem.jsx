import style from "./FormItem.module.css";
import { images } from "../../assets";
import { useRef, useState } from "react";
import { createItem } from "../../api/itemApi";

const FormItem = () => {
  const inputRef = useRef(null);
  const [previewImg, setPreviewImg] = useState(null);
  const [cidade, setCidade] = useState("");
  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [categoria, setCategoria] = useState("");
  const [localRef, setLocalRef] = useState("");
  const [telefone, setTelefone] = useState("");

  const handleClick = () => {
    inputRef.current.click();
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

    const formData = new FormData();
    formData.append("nome", nome);
    formData.append("descricao", descricao);
    formData.append("imagem", imagem); // arquivo
    formData.append("categoria", categoria);
    formData.append("cidade", cidade);
    formData.append("localRef", localRef);
    formData.append("contato", telefone);

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

  return (
    <form onSubmit={handleSubmit} className={style.formItem} action="">
      <div onClick={handleClick}>
        {previewImg ? (
          <img className={style.imagemPreview} src={previewImg} alt="Prévia" />
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
        <p>Preencha tudo para uma boa busca!</p>

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
            placeholder="Ex: Cor, marca, tamanho"
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
          <label htmlFor="pistaCidade">Cidade:</label>
          <input
            id="pistaCidade"
            name="pistaCidade"
            type="text"
            placeholder="Digite o nome da cidade"
            value={cidade}
            onChange={(e) => setCidade(e.target.value)}
          />
        </div>

        <div>
          <label htmlFor="pistaLocalReferencia">Pista Local Referência:</label>
          <input
            name="pistaLocalReferencia"
            type="text"
            required
            placeholder="Ex: Perto da escola"
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

      <button type="submit">Cadastrar</button>
    </form>
  );
};

export default FormItem;
