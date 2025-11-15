import { images } from "../../assets";
import style from "./RegisterUser.module.css";
import { registerUser } from "../../api/auth";
import { useState } from "react";
import { showSuccess } from "../../service/ToasTservice";
import { useNavigate } from "react-router-dom";
import Loading from "../../components/modal/Loading";

const RegisterUser = () => {
  const [nome, setNome] = useState(null);
  const [email, setEmail] = useState(null);
  const [senha, setSenha] = useState(null);
  const [telefone, setTelefone] = useState(null);
  const [loading, setLoading] = useState(false);
  const [erros, setErros] = useState({});
  const navgate = useNavigate();

  const handleRegister = async (e) => {
    setLoading(true);
    e.preventDefault();
    try {
      await registerUser({
        nome,
        email,
        senha,
        contato: telefone,
      });
      showSuccess("Cadastro concluído");
      setTimeout(() => {
        navgate("/");
      }, 2000);
    } catch (error) {
      if (error.response?.data) {
        console.log(error.response.data);
        setErros(error.response.data);
      } else {
        console.error("Erro inesperado:", error);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className={style.form_Register} onSubmit={handleRegister}>
      <div>
        <img src={images.sherdog} alt="" />
        <h2>Perdidos e Achados</h2>
      </div>
      <section className={style.box_section}>
        <div>
          {erros.nome && <p className="erro">{erros.nome}</p>}
          <input
            type="text"
            placeholder="Nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />
        </div>

        <div>
          {erros.email && <p className="erro">{erros.email}</p>}
          <input
            type="email"
            placeholder="E-mail"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div>
          {erros.senha && <p className="erro">{erros.senha}</p>}
          <input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
        </div>
        <div>
          {erros.contato && <p className="erro">{erros.contato}</p>}
          <input
            type="text"
            placeholder="Telefone"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
          />
        </div>
      </section>
      <button type="submit">Cadastrar</button>
      <Loading img={images.sherdog} text={"Cadastrando"} visible={loading} />
    </form>
  );
};

export default RegisterUser;
