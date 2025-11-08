import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/auth";
import { images } from "../../assets";
import style from "./Login.module.css";
import { showSuccess } from "../../service/ToasTservice";

const Login = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setErro("");
    try {
      await login({ email, senha });
      showSuccess("Login feito com sucesso!");
      setTimeout(() => {
        navigate("/");
      }, 2000);
    } catch (err) {
      setErro(err?.data || "Tente mais tarde");
    }
  };

  return (
    <form className={style.form_login} onSubmit={handleLogin}>
      <div>
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

        <button type="submit">Entrar</button>

        {erro && <p className={style.erro}>{erro}</p>}

        <div>
          <h3 onClick={() => navigate("/registrar")}>Cadastre-se</h3>
          <img src={images.sherdogNew} alt="" />
        </div>
      </section>
    </form>
  );
};

export default Login;
