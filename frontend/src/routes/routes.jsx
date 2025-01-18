import React from "react";
import { Route, BrowserRouter as Router, Routes as Routes_react } from "react-router-dom";
import RouteGuard from "./RouteGuard.jsx"

//pages

import Login from "../pages/login.jsx"
import Test from "../pages/test.jsx"


function Routes() {
    return (
        <Router>
            <Routes_react>
                <Route path="/"
                    element={
                        <Login />
                    }
                />
                <Route path="/test"
                    element={
                        <Test />
                    }
                />

            </Routes_react>
        </Router>
    );
}

export default Routes
