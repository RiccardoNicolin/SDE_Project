import React from 'react';
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from 'react';
import { fetchPlant } from '../services/apiService';
import LogoutButton from '../components/Logout_Button';
import PlantComponent from '../components/PlantComponent';
import { useNavigate } from 'react-router-dom';
import WeatherComp from '../components/weather_comp';
import CompGeo from '../components/comp_geo';

const Test = () => {
    const navigate = useNavigate();
    const handleNewPlant = () => {
        navigate('/NewPlant');
     };

    let token = sessionStorage.getItem("token");
    let user = jwtDecode(token).sub;

    const [plantData, setPlantData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await fetchPlant(token, user);
                setPlantData(data);
                //console.log('Plant data:', data);
            } catch (error) {
                console.error('Error fetching plant data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
           <h3>Benvenuto {user}</h3>
            <LogoutButton />
           {plantData == null ? <p>No saved plants</p> : plantData.map((element) => <PlantComponent name={element.plantname} location={element.place} planted={element.startTimeToHarvest} estimate={element.endTimeToHarvest} plantId={element.id}/>)}

           <button onClick={handleNewPlant}>INSERISCI UNA NUOVA PIANTA</button>
           <WeatherComp />
        </div>
    );
};

export default Test;