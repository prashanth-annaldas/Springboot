import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "./pages/login";
import Register from "./pages/register";
import Home from "./pages/home";
import SaveProfile from "./pages/profile";

function App(){

    return(

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
                    path="/saveProfile"
                    element={<SaveProfile />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;