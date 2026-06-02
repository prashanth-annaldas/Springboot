import { useEffect, useState } from "react";
import api from "../services/api";

const EnrolledCourses = () => {
    const [enrollments, setEnrollments] = useState([]);

    useEffect(() => {
        loadEnrollments();
    }, []);

    const loadEnrollments = async () => {

        const res = await api.get(
            "/api/student/enrollments"
        );
        setEnrollments(res.data);
    };

  return (
        <div className="card">
            <h2 className="mb-4">My Enrolled Courses & Grades</h2>
            {enrollments.length === 0 ? (
                <div className="empty-state">
                    <div className="empty-icon">📊</div>
                    <p className="empty-title">No courses yet</p>
                    <p className="empty-desc">You haven't enrolled in any courses. Browse the available course electives list above to begin.</p>
                </div>
            ) : (
                <div className="table-container">
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
            )}
        </div>
  );
}

export default EnrolledCourses;