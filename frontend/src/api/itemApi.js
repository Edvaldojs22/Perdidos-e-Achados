import api from "./axios";

export const todoItens = async () => {
  try {
    const response = await api.get("/api/itens");

    return response;
  } catch (error) {
    console.log(error.response);
    throw error;
  }
};

export const createItem = async (formData) => {
  try {
    const response = await api.post("/api/item/cadastrar", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return response;
  } catch (error) {
    console.log(error.response);
    throw error;
  }
};

export const itemInfo = async (itemId) => {
  try {
    const response = await api.get(`/api/item/${itemId}`);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const itensDoUsuario = async () => {
  try {
    const response = await api.get("/api/meus-itens");
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};


export const editarItem = async (itemId, formData) => {
  try {
    const respose = await api.put(`/api/item/${itemId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      withCredentials: true,
    });

    return respose;
  } catch (error) {
    console.log(error.response);
    throw error;
  }
};

export const excluirItem = async (itemId) => {
  try {
    const response = await api.delete(`/api/item/${itemId}`);
    console.log(response);
    return response;
  } catch (error) {
    console.log(error.response);
    throw error;
  }
};
