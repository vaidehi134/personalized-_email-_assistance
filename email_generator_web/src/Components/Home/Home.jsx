import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Home.module.css";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Your Personal Email Assistant </h1>
      <button
        className={styles.button + " " + styles.buttonBlue}
        onClick={() => navigate("/email-reply-generator")}
      >
        Go to Email Reply Generator
      </button>
      <button
        className={styles.button + " " + styles.buttonGreen}
        onClick={() => navigate("/email-message-generator")}
      >
        Go to Email Message Generator
      </button>
    </div>
  );
};

export default Home;
