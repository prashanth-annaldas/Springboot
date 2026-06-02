import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

const ForgotPassword = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleForgotPassword = async (e) => {
        e.preventDefault();
        try {

            const response = await api.post("/api/auth/forgot-password", {
                email,
                newPassword: password
            });

            if(response.data.message === "USER DOES NOT EXISTED") {
                alert("USER NOT EXISTED");
                return;
            }
            navigate("/login");

        } catch (error) {
            console.error("Error occurred while resetting password:", error);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card animate-fade-in">
                <div className="text-center mb-6">
                    <h2 className="mb-2">Forgot Password</h2>
                </div>

                <form onSubmit={handleForgotPassword}>
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
                        <label className="form-label">New Password</label>
                        <input
                            type="password"
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: "100%", marginTop: "8px" }}>
                        Reset Password
                    </button>
                </form>

                <div className="text-center mt-4">
                    <p className="text-muted" style={{ fontSize: "14px" }}>
                        Don't have an account? <a href="/register">Register here</a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default ForgotPassword;