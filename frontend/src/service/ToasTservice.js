import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const showSuccess = (mensagem) => {
  toast.success(mensagem, {
    position: "top-right",
    autoClose: 3000,
    className: "toast-success",
  });
};

export const showWarning = (mensagem) => {
  toast.warn(mensagem, {
    position: "top-right",
    autoClose: 4000,
    className: "toast-warning",
    icon: "⚠️",
  });
};
