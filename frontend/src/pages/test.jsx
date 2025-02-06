import React from 'react';
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from 'react';
import { fetchPlant } from '../services/apiService';
import LogoutButton from '../components/Logout_Button';

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
           <h3>Benvenuto {user}</h3>
            <LogoutButton />
           {plantData == null ? <p>Nessuno Piante</p> : plantData.map((element) => <p key={element.id}> {element.plantname}</p>)}

           <button>INSERISCI UNA NUOVA PIANTA</button>
        </div>
    );
};

export default Test;