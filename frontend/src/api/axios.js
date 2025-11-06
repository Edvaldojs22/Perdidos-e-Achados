import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

//Serv
api.interceptors.request.use((config) => {
  const token =
    "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwZXJkaWRvcy1hY2hhZG9zLWFwaSIsInN1YiI6Imx1Y2FzLjEwQGdtYWlsLmNvbSIsImlhdCI6MTc2MjQ1OTUzMywiZXhwIjoxNzYyNDYzMTMzfQ.NPONMaWvVlzGV4lZk4nVb5BaPrMA6GucDJTLbJZCdio";
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
