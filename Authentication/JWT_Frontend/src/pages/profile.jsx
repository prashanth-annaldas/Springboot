import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function SaveProfile() {
  const navigate = useNavigate();

  const [dob, setDob] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    await api.post("/api/auth/saveProfile", { dob });

    navigate("/home");
  };

  return (
    <div>
      <h1>Save Profile</h1>

      <form onSubmit={handleSubmit}>
        <input type="date" onChange={(e) => setDob(e.target.value)} />

        <br />
        <br />

        <button type="submit">Save</button>
      </form>
    </div>
  );
}

export default SaveProfile;
