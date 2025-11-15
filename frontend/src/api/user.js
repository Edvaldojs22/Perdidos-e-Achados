import api from "./axios";

export const excluirUser = async () => {
  return api.delete("/api/usuario");
};
