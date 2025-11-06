import api from "./axios";
export const login = async (email, senha) => {
  try {
    const respose = await api.post("/auth/login", {
      email,
      senha,
    });

    const token = respose.data.token;
    localStorage.setItem("token", token);
    return respose.data;
  } catch (error) {
    console.log(error);
    throw error.response;
  }
};
