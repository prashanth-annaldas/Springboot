import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {

            const response = await api.post(
                "/api/auth/mylogin",
                {
                    email,
                    password
                }
            );

            if(response.data.message === "USER NOT EXISTED") {
                alert("USER NOT EXISTED");
            }
            else if(response.data.message === "INCORRECT CREDENTIALS") {
                alert("INCORRECT CREDENTIALS");
            }
            else if(response.data.message === "ACCOUNT NOT APPROVED YET") {
                alert("ACCOUNT NOT APPROVED YET");
            }
            else {
                if(response.data.role === "ADMIN") {
                    navigate("/admin");
                }
                else if(response.data.role === "STUDENT") {
                    navigate("/student");
                }
                else {
                    navigate("/faculty");
                }
            }

        } catch (error) {

            alert("Login Failed");

            console.log(error);

        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card animate-fade-in">
                <div className="text-center mb-6">
                    <h2 className="mb-2">LOGIN</h2>
                </div>

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label className="form-label">Email Address</label>
                        <input
                            type="email"
                            placeholder="name@gmail.com"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Password</label>
                        <input
                            type="password"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: "100%", marginTop: "8px" }}>
                        Sign In
                    </button>
                </form>

                <div className="text-center mt-4">
                    <p className="text-muted" style={{ fontSize: "14px" }}>
                        Don't have an account? <a href="/register">Register here</a>
                    </p>
                </div>
                <div>
                    <p>
                        <a href="/forgot-password">Forgot Password?</a>
                    </p>
                </div>
            </div>
        </div>
    );
}

export default Login;