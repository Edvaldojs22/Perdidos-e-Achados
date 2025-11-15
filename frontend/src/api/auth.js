import api from "./axios";

export const login = async ({ email, senha }) => {
  const response = await api.post("/auth/login", {
    email,
    senha,
  });

  const token = response.data.token;
  const nome = response.data.nome;
  const telefone = response.data.contato;

  localStorage.setItem("token", token);
  localStorage.setItem("nome", nome);
  localStorage.setItem("telefone", telefone);
  return response;
};

export const registerUser = async ({ nome, email, senha, contato }) => {
  const response = await api.post("/auth/cadastrar", {
    nome,
    email,
    senha,
    contato,
  });

  const token = response.data.token;
  const nomeUsuario = response.data.nome;
  const telefone = response.data.contato;
  localStorage.setItem("token", token);
  localStorage.setItem("nome", nomeUsuario);
  localStorage.setItem("telefone", telefone);
  return response;
};
