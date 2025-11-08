import api from "./axios";

export const getAllItens = async () => {
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
    const respose = await api.post("/api/item/cadastrar", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return respose;
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
