import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { itemInfo } from "../../../api/itemApi";
import FormItem from "./FormItem";

const FormEdit = () => {
  const { itemId } = useParams();
  const [item, setItem] = useState(null);

  useEffect(() => {
    const fetchItem = async () => {
      try {
        const response = await itemInfo(itemId);
        setItem(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchItem();
  }, []);

  return <FormItem modo="editar" item={item} />;
};

export default FormEdit;
