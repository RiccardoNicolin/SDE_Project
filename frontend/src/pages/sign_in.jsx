import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { SignIn } from '../services/apiService';

const SignInPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {

        try {
            if (username.length == 0 || password.length == 0) {
                console.log('Inserire username e password');
            }

            else {
                console.log('Start Sign In Process');
                const response = await SignIn(username, password);
                console.log('Login successful:', response);
                sessionStorage.setItem('token', response.data.token);
                navigate('/login',);
            }
        } catch (error) {
            console.error('Login failed:', error.response ? error.response.data : error.message);
        }
    };

    return (
        <div style={{ justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
            <h2>Sign-In</h2>
            <label>
                Username:
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
            </label>
            <label>
                Password:
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
            </label>
            <button onClick={handleLogin}>Sign In</button>
            <hr />
            <button onClick={() => navigate('/')}>Go to Login</button>
        </div>

    );
};

export default SignInPage;