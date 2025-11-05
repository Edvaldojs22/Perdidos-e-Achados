import api from "./axios";

export const getAllItens = async () => {
  return api.get("/api/items");
};
