import React from 'react';
import { useNavigate } from 'react-router-dom';
import PlantInfo from '../components/plant_info';
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
const InfoPage = () => {
    const navigate = useNavigate();

    const handleBackClick = () => {
        navigate('/test');
    };
    const location = useLocation();
    const [info, setInfo] = useState({});

    useEffect(() => {
        if (location.state && location.state.info) {
            setInfo(location.state.info);
        }
    }, [location.state]);


    return (
        console.log(info),
    <div>
        <PlantInfo info={info}/>
        <button onClick={handleBackClick}>Back</button>
    </div>
);
};

export default InfoPage;