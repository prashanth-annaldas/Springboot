import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function login() {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: "",
    password: "",
  });
  const handleChange = (e) => {
    setUser((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await api.post("/api/auth/mylogin", user);

    if (res.data === "PROFILE_REQUIRED") {
      navigate("/saveProfile");
    } else if (res.data === "LOGIN_SUCCESS") {
      navigate("/home");
    } else {
      alert("Login failed");
    }
  };
  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
        />
        <button type="submit">Login</button>
      </form>

      <a href="/register">Register</a>
    </div>
  );
}

export default login;
