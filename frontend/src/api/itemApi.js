import api from "./axios";

export const todoItens = async () => {
  return api.get("/api/itens");
};

export const createItem = async (formData) => {
  const response = await api.post("/api/item/cadastrar", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });

  return response;
};

export const itemInfo = async (itemId) => {
  return api.get(`/api/item/${itemId}`);
};

export const itensDoUsuario = async () => {
  return api.get("/api/meus-itens");
};

export const editarItem = async (itemId, formData) => {
  return api.put(`/api/item/${itemId}`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
    withCredentials: true,
  });
};

export const excluirItem = async (itemId) => {
  return api.delete(`/api/item/${itemId}`);
};
