import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "./pages/login";
import Register from "./pages/register";
import Home from "./pages/home";
import Admin from "./pages/admin";
import StudentDashboard from "./pages/student";
import FacultyDashboard from "./pages/faculty";
import EnrolledCourses from "./pages/enrolledCourses";
import ForgotPassword from "./pages/forgot-password";

function App() {

    return (
        <BrowserRouter>

            <Routes>

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/home"
                    element={<Home />}
                />

                <Route
                    path="/admin"
                    element={<Admin />}
                />

                <Route
                    path="/student"
                    element={<StudentDashboard />}
                />

                <Route
                    path="/faculty"
                    element={<FacultyDashboard />}
                />

                <Route
                    path="/enrolled-courses"
                    element={<EnrolledCourses />}
                />

                <Route
                    path="/forgot-password"
                    element={<ForgotPassword />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;