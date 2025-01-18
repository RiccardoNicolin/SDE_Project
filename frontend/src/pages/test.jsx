import React from 'react';

const Test = () => {
    const user = sessionStorage.getItem('user');

    return (
        <div>
            <h1>User Information</h1>
            <p>{user ? user : 'No user information available'}</p>
        </div>
    );
};

export default Test;