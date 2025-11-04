import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/auth";

const Login = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setErro("");
    try {
      const response = await login(email, senha);
      console.log(response);
      navigate("/home"); // redireciona para rota protegida
    } catch (err) {
      setErro(err?.message || "Erro ao fazer login");
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "0 auto" }}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Senha:</label>
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
        </div>

        <button type="submit">Entrar</button>
      </form>

      {erro && <p style={{ color: "red" }}>{erro}</p>}
    </div>
  );
};

export default Login;
