import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Register() {

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        department: "",
        phone: ""
    });
    const navigate = useNavigate();

    const handleChange = (e) => {

        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });

    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            const response = await api.post(
                "/api/auth/myregister",
                formData
            );
            if(response.data === "REGISTRATION SUCCESSFULL") {
                navigate("/login");
            }

        } catch (error) {

            console.log(error);

            alert("Registration Failed");

        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card animate-fade-in" style={{ maxWidth: "500px" }}>
                <div className="text-center mb-6">
                    <h2 className="mb-2">Create Account</h2>
                    <p className="text-muted">Register as a student to access the academic portal</p>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="form-label">Full Name</label>
                        <input
                            type="text"
                            name="name"
                            placeholder="Name..."
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Email Address</label>
                        <input
                            type="email"
                            name="email"
                            placeholder="name@gmail.com"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Password</label>
                        <input
                            type="password"
                            name="password"
                            placeholder="••••••••"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Department</label>
                        <input
                            type="text"
                            name="department"
                            placeholder="Computer Science"
                            value={formData.department}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">Phone Number</label>
                        <input
                            type="text"
                            name="phone"
                            placeholder="+91 00000 00000"
                            value={formData.phone}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ width: "100%", marginTop: "8px" }}>
                        Register Account
                    </button>
                </form>

                <div className="text-center mt-4">
                    <p className="text-muted" style={{ fontSize: "14px" }}>
                        Already have an account? <a href="/login">Login here</a>
                    </p>
                </div>
            </div>
        </div>
    );
}

export default Register;