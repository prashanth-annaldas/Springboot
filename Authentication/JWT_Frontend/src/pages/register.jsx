import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function register() {
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
    const res = await api.post("/api/auth/myregister", user);
    if (res.data === "REGISTER_SUCCESS") {
      navigate("/login");
    }
    if(res.data === "ALREADY_EXIST"){
        alert("User already exists");
    }
  };
  return (
    <div>
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          onChange={handleChange}
        /> <br /> <br />
        <input
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
        /> <br /> <br />
        <button type="submit">Register</button>
      </form> <br />

      <a href="/login">login</a>
    </div>
  );
}

export default register;
