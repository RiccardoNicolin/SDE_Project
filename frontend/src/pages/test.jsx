import React from 'react';
import { jwtDecode } from "jwt-decode";

const Test = () => {
    let token = sessionStorage.getItem("token");
    let user = jwtDecode(token).username;
    let id = jwtDecode(token).id;

    return (
        <div>
            <h1>User Information</h1>
            <p>{user ? user : 'No user information available'}</p>
            <p>{id ? id : 'No user information available'}</p>
        </div>
    );
};

export default Test;