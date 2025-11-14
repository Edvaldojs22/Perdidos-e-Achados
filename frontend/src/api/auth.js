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
  return response.data;
};

export const registerUser = async ({ nome, email, senha, contato }) => {
  try {
    const response = await api.post("/auth/cadastrar", {
      nome,
      email,
      senha,
      contato,
    });

    const token = response.data.token;
    localStorage.setItem("token", token);
    return response.data;
  } catch (error) {
    throw error.response;
  }
};
