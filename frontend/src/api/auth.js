import api from "./axios";

export const login = async ({ email, senha }) => {
  try {
    const response = await api.post("/auth/login", {
      email,
      senha,
    });

    const token = response.data.token;
    localStorage.setItem("token", token);
    return response.data;
  } catch (error) {
    throw error.response;
  }
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
