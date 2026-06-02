import { useState, useEffect } from "react";
import api from "../services/api";

const StudentDashboard = () => {

    const [courses, setCourses] = useState([]);
    const [enrollments, setEnrollments] = useState([]);
    const [activeTab, setActiveTab] = useState("portal");
    const [studentProfile, setStudentProfile] = useState(null);
    const totalGrades = enrollments.reduce(
        (sum, e) => sum + Number(e.grade || 0),
        0
    );

    const totalCourses = enrollments.length;

    const overallGPA =
        totalCourses > 0
            ? (totalGrades / totalCourses).toFixed(2)
            : "N/A";

    useEffect(() => {
        loadCourses();
        loadEnrollments();
        profile();
    }, []);

    const loadCourses = async () => {

        const res = await api.get(
            "/api/student/courses"
        );

        setCourses(res.data);
    };

    const loadEnrollments = async () => {
        try {
            const res = await api.get(
                "/api/student/enrollments"
            );
            setEnrollments(res.data);
        } catch (error) {
            console.error("Failed to load enrollments", error);
        }
    };

    const handleEnroll = async (courseId) => {

        try {

            const res = await api.post(
                `/api/student/enroll/${courseId}`
            );

            alert(res.data);

            loadEnrollments();

        } catch(error) {

            console.log(error);

            alert("Enrollment Failed");
        }
    };

    const handleLogout =  async ()=>{
        await api.get("/api/auth/mylogout");
        window.location.href = "/login";
    }

    const availableCourses = courses.filter(
        (course) => !enrollments.some((e) => e.courseName === course.courseName)
    );

    const profile = async ()=>{
        try{
            const res = await api.get("/api/student/profile");
            setStudentProfile(res.data);
        } catch (error) {
            console.error("Failed to load student profile", error);
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
                    <div className={`sidebar-item ${activeTab === "portal" ? "active" : ""}`}>
                        <a href="#portal" onClick={(e) => { e.preventDefault(); setActiveTab("portal"); }}>
                            <span>📖</span> Student Portal
                        </a>
                    </div>
                    <div className={`sidebar-item ${activeTab === "enrolled" ? "active" : ""}`}>
                        <a href="#enrolled" onClick={(e) => { e.preventDefault(); setActiveTab("enrolled"); }}>
                            <span>👤</span> Enrolled courses
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
                        <h1>{activeTab === "portal" ? "Student Portal" : "Enrolled Courses"}</h1>
                        <p className="text-muted">
                            {activeTab === "portal"
                                ? "Welcome to your academic center. Enroll in courses and track your grades."
                                : "Review your enrolled courses and view your academic grades."}
                        </p>
                    </div>
                    <div className="user-badge">
                        <div className="avatar">ST</div>
                        <div>
                            <div style={{ fontWeight: 600, color: "var(--text-main)" }}><a href="#profile" onClick = {(e)=>{ e.preventDefault(); setActiveTab("profile"); }}>Student Profile</a></div>
                            <div style={{ fontSize: "12px", color: "var(--text-muted)" }}>Enrolled Student</div>
                        </div>
                    </div>
                </header>

                <main className="main-content">
                    {/* Stats */}
                    <div className="dashboard-grid" style={{ gridTemplateColumns: "repeat(auto-fit, minmax(240px, 1fr))", marginBottom: "32px" }}>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Enrolled Courses</span>
                                <span className="stat-val">{enrollments.length}</span>
                            </div>
                            <div className="stat-icon">📚</div>
                        </div>
                        <div className="stat-card">
                            <div className="stat-info">
                                <span className="stat-label">Available Electives</span>
                                <span className="stat-val">{availableCourses.length}</span>
                            </div>
                            <div className="stat-icon" style={{ backgroundColor: "var(--primary-glow)", color: "var(--primary)" }}>➕</div>
                        </div>
                    </div>

                    {/* Conditional content based on activeTab */}
                    {activeTab === "portal" ? (
                        /* Available Courses */
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">Available Courses</h2>
                            {availableCourses.length === 0 ? (
                                <div className="empty-state">
                                    <div className="empty-icon">🎓</div>
                                    <p className="empty-title">All courses enrolled</p>
                                    <p className="empty-desc">You are currently enrolled in all available courses or there are no new classes offered this semester.</p>
                                </div>
                            ) : (
                                <div className="dashboard-grid" style={{ marginTop: "16px" }}>
                                    {availableCourses.map((course) => (
                                        <div key={course.id} className="card" style={{ display: "flex", flexDirection: "column", justifyContent: "space-between", height: "160px", padding: "20px" }}>
                                            <div>
                                                <span style={{ fontSize: "12px", fontWeight: "600", color: "var(--primary)", textTransform: "uppercase", letterSpacing: "0.05em" }}>Course Elective</span>
                                                <h3 style={{ fontSize: "17px", marginTop: "4px" }}>{course.courseName}</h3>
                                            </div>
                                            <button
                                                className="btn btn-primary"
                                                style={{ width: "100%", padding: "8px 16px", fontSize: "14px", marginTop: "12px" }}
                                                onClick={() => handleEnroll(course.id)}
                                            >
                                                Enroll in Course
                                            </button>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    ) : activeTab === "enrolled" ? (
                        /* Enrolled Courses list with Grades */
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">My Enrolled Courses & Grades</h2>
                            {enrollments.length === 0 ? (
                                <div className="empty-state">
                                    <div className="empty-icon">📊</div>
                                    <p className="empty-title">No courses yet</p>
                                    <p className="empty-desc">You haven't enrolled in any courses. Browse the available course electives list in the Student Portal to begin.</p>
                                </div>
                            ) : (
                                <div className="table-container">
                                    <div style={{ marginBottom: "12px" }}>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Course Title</th>
                                                    <th>Academic Grade</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {enrollments.map((enrollment, index) => (
                                                    <tr key={index}>
                                                        <td style={{ fontWeight: 500 }}>{enrollment.courseName}</td>
                                                        <td>
                                                            <span className={enrollment.grade ? "badge badge-success" : "badge badge-warning"}>
                                                                {enrollment.grade ?? "Not Graded"}
                                                            </span>
                                                        </td>
                                                    </tr>
                                                ))}
                                            </tbody>
                                        </table>
                                    </div>
                                    <div>
                                        <h2 style={{ marginTop: "16px" }}>Overall GPA: {overallGPA}</h2>
                                    </div>
                                </div>
                            )}
                        </div>
                    )
                    : (
                        <div className="card mb-6 animate-fade-in">
                            <h2 className="mb-4">Student Profile</h2>
                            <div style={{ display: "flex", alignItems: "center", gap: "24px" }}>
                                <div className="avatar avatar-lg">ST</div>
                                <div>
                                    <h3 style={{ fontSize: "18px", fontWeight: 600 }}>{studentProfile?.studentName || "John Doe"}</h3>
                                    <span>{studentProfile?.email}</span>, 
                                    <span> {studentProfile?.department}</span>, 
                                    <span> {studentProfile?.phone}</span>
                                </div>
                            </div>
                        </div>
                        )}
                </main>
            </div>
        </div>
    );
};

export default StudentDashboard;