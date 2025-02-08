import React, { useState } from 'react';
import { SearchPlant } from '../services/apiService';
import PlantInfo from './plant_info';
import { useNavigate } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import { addPlantToUser } from '../services/apiService';


const NewPlantComp = () => {
    const [plantName, setPlantName] = useState('');
    const [plantData, setPlantData] = useState(null);
    const navigate = useNavigate();

    const addPlant = async () => { // Add plant to user's list
        let token = sessionStorage.getItem("token");
        let user = jwtDecode(token).sub;
        const response = await addPlantToUser(plantData.name, user, token);
        console.log('Plant added:', response);
        navigate("/test");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await SearchPlant(plantName);
            setPlantData(response);
        } catch (error) {
            console.error('Error submitting plant name:', error);
        };
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Plant Name:
                    <input
                        type="text"
                        value={plantName}
                        onChange={(e) => setPlantName(e.target.value)}
                    />
                </label>
                <button type='submit'>Submit</button>
            </form>

            {plantData ? (
                <div>
                    <PlantInfo info={plantData} />
                    <button onClick={addPlant}>Add Plant</button>
                </div>
            ) : (
                <p>No data</p>
            )}

        </div>
    );
};

export default NewPlantComp;