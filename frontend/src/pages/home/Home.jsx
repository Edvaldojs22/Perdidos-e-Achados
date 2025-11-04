import { images } from "../../assets";
import ItemCard from "../../components/cardItem/ItemCard";
import style from "./Home.module.css";
import { BsSearch } from "react-icons/bs";

const Home = () => {
  return (
    <main>
      <section>
        <header>
          <img src="" alt="" />
          <h1>Peridos e Achados</h1>
        </header>

        <div className={style.box_input}>
          <BsSearch />
          <input type="text" placeholder="Nome" name="" id="" />
          <img src="" alt="" />
        </div>
      </section>

      <div>
        <h2>Items Perdidos</h2>
        <select name="Cidade" id="cidade">
          <option value="">Cidade</option>
          <option value="">Campina</option>
          <option value="">RJ</option>
        </select>
      </div>

      <section className={style.boxScroll}>
        <div className={style.boxItens}>
          <ItemCard
            imageUrl="https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
            cidade="Riacho"
            nome="Gato Mc"
            postado="02/02/2222"
          />
          <ItemCard
            imageUrl="https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
            cidade="Riacho"
            nome="Gato Mc"
            postado="02/02/2222"
          />
          <ItemCard
            imageUrl="https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
            cidade="Riacho"
            nome="Gato Mc"
            postado="02/02/2222"
          />
          <ItemCard
            imageUrl="https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
            cidade="Riacho"
            nome="Gato Mc"
            postado="02/02/2222"
          />
          <ItemCard
            imageUrl="https://www.ahnegao.com.br/wp-content/uploads/2025/02/imgaleat-6jx-3.jpg"
            cidade="Riacho"
            nome="Gato Mc"
            postado="02/02/2222"
          />
        </div>
      </section>

      <button>Carregar</button>
      <img src={images.box} alt="Novo item" />
    </main>
  );
};

export default Home;
