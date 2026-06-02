import { useEffect, useState } from "react";
import api from "../services/api";

const FacultyDashboard = () =>{
    const [enrollments, setEnrollments] = useState([]);
    const [activeTab, setActiveTab] = useState("portal");
    const [facultyProfile, setFacultyProfile] = useState(null);

    useEffect(() => {
        loadEnrollments();
        profile();
    }, []);

    const loadEnrollments = async () => {

        const res = await api.get("/api/faculty/students");
        setEnrollments(res.data);
    };

    const updateGrade = async (enrollmentId, grade) => {

        await api.put(
            `/api/faculty/grade/${enrollmentId}?grade=${grade}`
        );

        alert("Grade Updated");

        loadEnrollments();
    };

    const handleLogout =  async ()=>{
        await api.get("/api/auth/mylogout");
        window.location.href = "/login";
    }

    const uniqueCoursesCount = new Set(enrollments.map((e) => e.courseName)).size;

    const profile = async ()=>{
        try{
            const res = await api.get("/api/faculty/profile");
            setFacultyProfile(res.data);
        } catch (error) {
            console.error("Failed to load faculty profile", error);
        }
    }

    return (
        <div className="app-container">
            {/* Sidebar */}
            <aside className="sidebar">
                <div className="sidebar-header">
                    <div className="sidebar-logo">Aegis University</div>
                </div>
                <nav className="sidebar-menu">
                    <div className="sidebar-item active">
                        <a href="/faculty" onClick={(e) => { e.preventDefault(); setActiveTab("portal"); }}>
                            <span>👩‍🏫</span> Faculty Portal
                        </a>
                    </div>
                </nav>
                <div className="sidebar-footer">
                    <button onClick={handleLogout} className="btn btn-logout">
                        Logout
                    </button>
                </div>
            </aside>
                <div className="main-layout animate-fade-in">
                    {/* Header */}
                    <header className="header-bar">
                        <div>
                        <h1>Faculty Dashboard</h1>
                        <p className="text-muted">Manage assigned courses, review enrolled students, and submit final grades.</p>
                    </div>
                    <div className="user-badge">
                        <div className="avatar">FC</div>
                        <div>
                            <div style={{ fontWeight: 600, color: "var(--text-main)" }}><a href="/faculty/profile" onClick={(e) => { e.preventDefault(); setActiveTab("profile"); }}>Faculty Professor</a></div>
                            <div style={{ fontSize: "12px", color: "var(--text-muted)" }}>Academic Instructor</div>
                        </div>
                    </div>
                </header>

                <main className="main-content">
                    {/* Dashboard Statistics */}
                    <div className="dashboard-grid" style={{ gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))", marginBottom: "32px" }}>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Assigned Students</span>
                                <span className="stat-val">{enrollments.length}</span>
                            </div>
                            <div className="stat-icon">👥</div>
                        </div>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Courses Teaching</span>
                                <span className="stat-val">{uniqueCoursesCount}</span>
                            </div>
                            <div className="stat-icon" style={{ backgroundColor: "var(--primary-glow)", color: "var(--primary)" }}>📖</div>
                        </div>
                    </div>

                    {(activeTab === "portal") && (
                        <div className="card">
                            <h2 className="mb-4">Student Grade Registry</h2>
                            {enrollments.length === 0 ? (
                                <div className="empty-state">
                                    <div className="empty-icon">📝</div>
                                    <p className="empty-title">No students enrolled</p>
                                    <p className="empty-desc">There are currently no students registered in your assigned courses.</p>
                                </div>
                            ) : (
                                <div className="table-container">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th>Student Name</th>
                                                <th>Course Title</th>
                                                <th>Grade Input</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {enrollments.map((enrollment) => (
                                                <tr key={enrollment.enrollmentId}>
                                                    <td style={{ fontWeight: 500 }}>{enrollment.studentName}</td>
                                                    <td className="text-muted">{enrollment.courseName}</td>
                                                    <td>
                                                        <input
                                                            type="number"
                                                            defaultValue={enrollment.grade || ""}
                                                            onChange={(e) => (enrollment.grade = e.target.value)}
                                                            placeholder="N/A"
                                                            style={{ maxWidth: "100px", padding: "8px 12px" }}
                                                        />
                                                    </td>
                                                    <td>
                                                        <button
                                                            className="btn btn-primary"
                                                            style={{ padding: "6px 14px", fontSize: "13px" }}
                                                            onClick={() =>
                                                                updateGrade(
                                                                    enrollment.enrollmentId,
                                                                    enrollment.grade
                                                                )
                                                            }
                                                        >
                                                            Save Grade
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
                    {(activeTab === "profile") && (
                <div className="card mb-6 animate-fade-in">
                    <h2 className="mb-4">Faculty Profile</h2>
                    <div style={{ display: "flex", alignItems: "center", gap: "24px" }}>
                        <div className="avatar avatar-lg">FC</div>
                        <div>
                            <h3 style={{ fontSize: "18px", fontWeight: 600 }}>{facultyProfile?.name || "Faculty Member"}</h3>
                            <span>{facultyProfile?.email}</span>
                        </div>
                    </div>
                </div>
            )}
                </main>
            </div>
        </div>
    );
}

export default FacultyDashboard;