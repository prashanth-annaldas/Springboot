import { useEffect, useState }
from "react";

import api from "../services/api";

import { useNavigate }
from "react-router-dom";

function Home() {

  const navigate = useNavigate();

  const [user, setUser] =
        useState(null);

  useEffect(() => {

    api.get("/api/auth/home")
      .then((response) => {

        setUser(response.data);
      });

  }, []);

  const handleLogout = async () => {

    await api.get(
        "/api/auth/mylogout"
    );

    navigate("/login");
  };

  if (!user) {

    return <h1>Loading...</h1>;
  }

  return (

    <div>

      <h1>Home</h1>

      <h2>
        Username:
        {user.username}
      </h2>

      <h2>
        DOB:
        {user.dob}
      </h2>

      <button
            onClick={handleLogout}
      >
            Logout
      </button>

    </div>
  );
}

export default Home;