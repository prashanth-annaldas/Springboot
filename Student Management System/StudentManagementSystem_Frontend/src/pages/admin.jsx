import { useEffect, useState } from "react";

import { getPendingStudents, approveStudent } from "../services/api";
import api from "../services/api";

function AdminDashboard(){

    const [activeTab, setActiveTab] = useState("dashboard"); // dashboard, faculty, create-faculty, establish-course
    const [students, setStudents] = useState([]);
    const [adminProfile, setAdminProfile] = useState(null);

    const loadStudents = async () => {

        const res = await api.get("/api/admin/pending-students");

        setStudents(res.data);
    };

    useEffect(() => {
        loadStudents();
    }, []);

    const handleApprove = async (id) => {

        await api.put(`/api/admin/approve/${id}`);

        loadStudents();
    };

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [department, setDepartment] = useState("");
    const [employeeId, setEmployeeId] = useState("");

    const createFaculty = async (e) => {

        e.preventDefault();

        try {

            const response = await api.post(
                "/api/admin/create-faculty",
                {
                    name,
                    email,
                    password,
                    department,
                    employeeId
                }
            );

            if(response.data === "FACULTY ALREADY EXISTS"){
                alert("Faculty with this email already exists");
            }
            else if(response.data === "FACULTY SAVED"){
                alert("Faculty created successfully");
                loadFaculties();
            }

            setName("");
            setEmail("");
            setPassword("");
            setDepartment("");
            setEmployeeId("");

        } catch (error) {

            console.log(error);

            alert("Failed to create faculty");
        }
    };

    const [faculties, setFaculties] = useState([]);
    const [facultyId, setFacultyId] = useState("");
    const [courseName, setCourseName] = useState("");

    useEffect(() => {
        loadFaculties();
        profile();
    }, []);

    const loadFaculties = async () =>{
        const res = await api.get("/api/admin/faculty-list");
        console.log("Faculty Response:", res.data);
        setFaculties(res.data);
    }

    const createCourse = async (e) =>{
        e.preventDefault();
        try {
            const response = await api.post(
                "/api/admin/create-course",
                {
                    courseName: courseName,
                    facultyId: parseInt(facultyId)
                }
            );

            if(response.data === "COURSE ALREADY EXISTED"){
                alert("Course already exists");
            }
            else if(response.data === "COURSE CREATED"){
                alert("Course created successfully");
            }
        }
        catch(er){
            console.log(er);
            alert("Failed to create course");
        }
    }

    const handleLogout =  async ()=>{
        await api.get("/api/auth/mylogout");
        window.location.href = "/login";
    }

    const profile = async ()=>{
        try{
            const res = await api.get("/api/admin/profile");
            setAdminProfile(res.data);
        } catch (error) {
            console.error("Failed to load admin profile", error);
        }
    }

    const removeFaculty = async (id) => {
        try {
            const response = await api.delete(`/api/admin/remove-faculty/${id}`);

            setFaculties(prev =>
                prev.filter(faculty => faculty.id !== id)
            );

            if(response.data === "FACULTY REMOVED"){
                alert("Faculty removed successfully");
            }
            else if(response.data === "FACULTY NOT FOUND"){
                alert("Faculty not found");
            }
            loadFaculties();
        } catch (error) {
            console.error("Failed to remove faculty", error);
            alert("Failed to remove faculty");
        }
    };

    return (
        <div className="app-container">
            {/* Sidebar */}
            <aside className="sidebar">
                <div className="sidebar-header">
                    <div className="sidebar-logo">Aegis University</div>
                </div>
                <nav className="sidebar-menu">
                    <div className={`sidebar-item ${activeTab === "dashboard" ? "active" : ""}`}>
                        <a href="#dashboard" onClick={(e) => { e.preventDefault(); setActiveTab("dashboard"); }}>
                            <span>📊</span> Dashboard
                        </a>
                    </div>
                    <div className={`sidebar-item ${activeTab === "faculty" ? "active" : ""}`}>
                        <a href="#faculty" onClick={(e) => { e.preventDefault(); setActiveTab("faculty"); }}>
                            <span>👩‍🏫</span> Faculty Members
                        </a>
                    </div>
                    <div className={`sidebar-item ${activeTab === "create-faculty" ? "active" : ""}`}>
                        <a href="#create-faculty" onClick={(e) => { e.preventDefault(); setActiveTab("create-faculty"); }}>
                            <span>➕</span> Register Faculty
                        </a>
                    </div>
                    <div className={`sidebar-item ${activeTab === "establish-course" ? "active" : ""}`}>
                        <a href="#establish-course" onClick={(e) => { e.preventDefault(); setActiveTab("establish-course"); }}>
                            <span>📖</span> Establish Course
                        </a>
                    </div>
                </nav>
                <div className="sidebar-footer">
                    <button onClick={handleLogout} className="btn btn-logout">
                        Logout
                    </button>
                </div>
            </aside>

            {/* Main Content Layout */}
            <div className="main-layout animate-fade-in">
                {/* Header */}
                <header className="header-bar">
                    <div>
                        <h1>
                            {activeTab === "dashboard" && "Admin Dashboard"}
                            {activeTab === "faculty" && "Faculty Members"}
                            {activeTab === "create-faculty" && "Register Faculty Member"}
                            {activeTab === "establish-course" && "Establish New Course"}
                        </h1>
                        <p className="text-muted">
                            {activeTab === "dashboard" && "Control panel for managing student registrations, faculty onboarding, and curriculum courses."}
                            {activeTab === "faculty" && "Directory of all registered faculty professors and academic instructors."}
                            {activeTab === "create-faculty" && "Onboard new faculty members by setting up their academic profiles and credentials."}
                            {activeTab === "establish-course" && "Create new courses in the curriculum and assign faculty instructors."}
                        </p>
                    </div>
                    <div className="user-badge">
                        <div className="avatar">AD</div>
                        <div>
                            <div style={{ fontWeight: 600, color: "var(--text-main)" }}><a href="#profile" onClick={(e) => { e.preventDefault(); setActiveTab("profile"); }}>System Admin</a></div>
                            <div style={{ fontSize: "12px", color: "var(--text-muted)" }}>Administrator Account</div>
                        </div>
                    </div>
                </header>

                <main className="main-content">
                    {/* Dashboard Statistics */}
                    <div className="dashboard-grid" style={{ gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))", marginBottom: "32px" }}>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Pending Students</span>
                                <span className="stat-val">{students.length}</span>
                            </div>
                            <div className="stat-icon" style={{ backgroundColor: "rgba(245, 158, 11, 0.15)", color: "var(--warning)" }}>⏳</div>
                        </div>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Active Faculty Members</span>
                                <span className="stat-val">{faculties.length}</span>
                            </div>
                            <div className="stat-icon">🎓</div>
                        </div>
                    </div>

                    {/* Conditional content based on activeTab */}
                    {activeTab === "dashboard" && (
                        /* Pending Students Table Card */
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">Pending Student Registrations</h2>
                            {students.length === 0 ? (
                                <div className="empty-state">
                                    <div className="empty-icon">✓</div>
                                    <p className="empty-title">All students approved</p>
                                    <p className="empty-desc">No new registration requests are currently pending approval.</p>
                                </div>
                            ) : (
                                <div className="table-container">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Student Name</th>
                                                <th>Email Address</th>
                                                <th>Action Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {students.map((student) => (
                                                <tr key={student.id}>
                                                    <td style={{ fontWeight: 500 }}>{student.name}</td>
                                                    <td className="text-muted">{student.email}</td>
                                                    <td>
                                                        <button
                                                            className="btn btn-primary"
                                                            style={{ padding: "6px 14px", fontSize: "13px" }}
                                                            onClick={() => handleApprove(student.id)}
                                                        >
                                                            Approve Student
                                                        </button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            )}
                        </div>
                    )}

                    {activeTab === "faculty" && (
                        /* Faculty List Card */
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">Registered Faculty Directory</h2>
                            {faculties.length === 0 ? (
                                <div className="empty-state">
                                    <div className="empty-icon">🎓</div>
                                    <p className="empty-title">No faculty members</p>
                                    <p className="empty-desc">No faculty accounts have been registered in the system yet.</p>
                                </div>
                            ) : (
                                <div className="table-container">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Faculty Name</th>
                                                <th>Email Address</th>
                                                <th>Department</th>
                                                <th>Employee ID</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {faculties.map((faculty) => (
                                                <tr key={faculty.id}>
                                                    <td style={{ fontWeight: 500 }}>{faculty.user?.name || "N/A"}</td>
                                                    <td className="text-muted">{faculty.user?.email || "N/A"}</td>
                                                    <td>{faculty.department || "N/A"}</td>
                                                    <td>
                                                        <span className="badge badge-success">
                                                            {faculty.employeeId || "N/A"}
                                                        </span>
                                                    </td>
                                                    <td><button onClick={() => removeFaculty(faculty.id)}>Remove</button></td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            )}
                        </div>
                    )}

                    {activeTab === "create-faculty" && (
                        /* Create Faculty Form */
                        <div className="card mb-6 animate-fade-in" style={{ maxWidth: "600px", margin: "0 auto" }}>
                            <h2 className="mb-4">Register Faculty Member</h2>
                            <form onSubmit={createFaculty}>
                                <div className="form-group">
                                    <label className="form-label">Full Name</label>
                                    <input
                                        type="text"
                                        placeholder="Dr. Sarah Connor"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="form-group">
                                    <label className="form-label">Email Address</label>
                                    <input
                                        type="email"
                                        placeholder="sconn@university.edu"
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

                                <div className="form-group">
                                    <label className="form-label">Academic Department</label>
                                    <input
                                        type="text"
                                        placeholder="Physics & Engineering"
                                        value={department}
                                        onChange={(e) => setDepartment(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="form-group">
                                    <label className="form-label">Employee Assignment ID</label>
                                    <input
                                        type="text"
                                        placeholder="EMP-80892"
                                        value={employeeId}
                                        onChange={(e) => setEmployeeId(e.target.value)}
                                        required
                                    />
                                </div>

                                <button type="submit" className="btn btn-primary" style={{ width: "100%", marginTop: "8px" }}>
                                    Register Faculty
                                </button>
                            </form>
                        </div>
                    )}

                    {activeTab === "establish-course" && (
                        /* Create Course Form */
                        <div className="card mb-6 animate-fade-in" style={{ maxWidth: "600px", margin: "0 auto" }}>
                            <h2 className="mb-4">Establish New Course</h2>
                            <form onSubmit={createCourse}>
                                <div className="form-group">
                                    <label className="form-label">Course Title</label>
                                    <input
                                        type="text"
                                        placeholder="PHY-201: Classical Mechanics"
                                        value={courseName}
                                        onChange={(e) => setCourseName(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="form-group">
                                    <label className="form-label">Assign Faculty Member</label>
                                    <select value={facultyId} onChange={(e) => setFacultyId(e.target.value)} required>
                                        <option value="">Select Assignee</option>
                                        {faculties.map((faculty) => (
                                            <option key={faculty.id} value={faculty.id}>
                                                {faculty.user?.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                <button type="submit" className="btn btn-primary" style={{ width: "100%", marginTop: "8px" }}>
                                    Create Course
                                </button>
                            </form>
                        </div>
                    )}
                    {(activeTab === "profile") && (
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">Admin Profile</h2>
                            <div style={{ display: "flex", alignItems: "center", gap: "24px" }}>
                                <div className="avatar avatar-lg">SA</div>
                                <div>
                                    <h3 style={{ fontSize: "18px", fontWeight: 600 }}>{adminProfile?.name || "System Admin"}</h3>
                                    <span>{adminProfile?.email}</span>
                                </div>
                            </div>
                        </div>
                    )}
                </main>
            </div>
        </div>
    );
}

export default AdminDashboard;