import React from 'react';
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from 'react';
import { fetchPlant } from '../services/apiService';

const Test = () => {
    let token = sessionStorage.getItem("token");
    let user = jwtDecode(token).sub;

    const [plantData, setPlantData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await fetchPlant(token, user);
                setPlantData(data);
                console.log('Plant data:', data);
            } catch (error) {
                console.error('Error fetching plant data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
           <p>Test {user}</p>
        </div>
    );
};

export default Test;