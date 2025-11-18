import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/auth";
import { images } from "../../assets";
import style from "./Login.module.css";
import { showSuccess } from "../../service/ToasTservice";

import { FaSpinner } from "react-icons/fa";

const Login = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErro("");
    try {
      await login({ email, senha });

      showSuccess("Login feito com sucesso!");
      setTimeout(() => {
        navigate("/");
      }, 2000);
    } catch (err) {
      setErro(err.response?.data || "Tente mais tarde");
    } finally {
      setLoading(false);
    }
  };

  const handleSetarUsuario = () => {
    setEmail("dev.test@gmail.com");
    setSenha("1234567");
  };

  return (
    <form className={style.form_login} onSubmit={handleLogin}>
      <div className={style.box_imgText}>
        <img className={style.sherdog} src={images.sherdog} alt="" />
        <h2>Perdidos e Achados</h2>
      </div>

      <section className={style.box_section}>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          placeholder="E-mail"
        />

        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? <FaSpinner className={style.spinner} /> : "Entrar"}
        </button>

        {erro && <p className={style.erro}>{erro}</p>}

        <div className={style.box_imgH3}>
          <h3 onClick={() => navigate("/registrar")}>Cadastre-se</h3>
          <img src={images.sherdogNew} alt="" />
        </div>
      </section>

      <div className={style.box_usuario}>
        <p>Use este usuário caso não queira criar uma conta agora</p>
        <p className={style.email}>
          <span>E-mail: </span> dev.test@gmail.com
        </p>
        <p className={style.senha}>
          {" "}
          <span>Senha: </span>1234567
        </p>
        <button type="button" onClick={() => handleSetarUsuario()}>
          Seta
        </button>
      </div>
    </form>
  );
};

export default Login;
