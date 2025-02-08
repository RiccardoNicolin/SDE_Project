import React from 'react';
import { useNavigate } from 'react-router-dom';
import LogoutButton from '../components/Logout_Button';
import NewPlantComp from '../components/new_plant';

const NewPlant = () => {
    const navigate = useNavigate();


    const handleGoBack = () => {
       navigate('/test');
    };

    return (
        <div>
            <h1>Add a new plant</h1>
            <NewPlantComp />
            <LogoutButton />
            <button onClick={handleGoBack}>Go Back</button>
        </div>
    );
};

export default NewPlant;