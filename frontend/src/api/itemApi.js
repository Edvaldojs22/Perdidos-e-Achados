import api from "./axios";

export const getAllItens = async () => {
  return api.get("/api/items");
};

export const createItem = async (formData) => {
  try {
    const respose = await api.post("/api/items/novo", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return respose.data;
  } catch (error) {
    console.log(error.response);
    throw error;
  }
};
