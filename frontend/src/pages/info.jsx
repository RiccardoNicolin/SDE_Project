import React from 'react';
import { useNavigate } from 'react-router-dom';
import PlantInfo from '../components/PlantInfo';
import CompGeo from '../components/comp_geo';

const InfoPage = () => {
    const navigate = useNavigate();

    const handleBackClick = () => {
        navigate('/test');
    };



    return (
    <div>
        <PlantInfo />
        <button onClick={handleBackClick}>Back</button>
    </div>
);
};

export default InfoPage;