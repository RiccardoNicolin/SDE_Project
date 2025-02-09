import React from 'react';
import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Remove the token from local storage
        sessionStorage.removeItem('token');
        // Redirect to the login page
        navigate('/');
    };

    return (
        <button 
            onClick={handleLogout} 
            style={{
                position: 'absolute',
                top: '10px',
                right: '10px',
                padding: '10px 20px',
            }}
        >
            Logout
        </button>
    );
};

export default LogoutButton;