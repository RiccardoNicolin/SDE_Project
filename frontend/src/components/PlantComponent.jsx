import React, { useState } from 'react';
import PropTypes from 'prop-types';
import ReactDOM from 'react-dom';
import { jwtDecode } from 'jwt-decode';
import { PlantAPlant, deletePlant } from '../services/apiService';
import { useNavigate } from 'react-router-dom';
import { SearchPlant } from '../services/apiService';
import PlantInfo from './plant_info';

const PlantComponent = ({ name, location, planted, estimate, plantId }) => {
    const [plantData, setPlantData] = useState('');
    const navigate = useNavigate();
    const style = {
        border: '1px solid green',
        padding: '10px',
        margin: '10px',
        borderRadius: '5px'
    };
    if (planted === "1970-01-01") {
        planted = "---";
        estimate = "---";
    }

    const handlePlant = async () => {
        let token = sessionStorage.getItem("token");
        let user = jwtDecode(token).sub;
        try{
        const response = await PlantAPlant(name, plantId, user, token);
       window.location.reload();
        }catch(error){
            console.error('Error planting:', error);
        }
    };

    const handleDelete = async () => { 
        let token = sessionStorage.getItem("token");
        try{
        const response = await deletePlant(plantId, token);
        window.location.reload();
        }catch(error){
            console.error('Error deleting plant:', error);
        }
    };

    const handleInfo = async () => {
            try {
                const response = await SearchPlant(name);
               setPlantData(response);
            } catch (error) {
                console.error('Error fetching plant info:', error);
            }
     };

    return (
        <>
        <div style={{ ...style, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div>
                <h5 style={{ display: 'inline', marginRight: '10px' }}>{name}</h5>
                <span style={{ display: 'inline', borderLeft: '1px solid rgba(0, 0, 0, 0.1)', paddingLeft: '10px', marginRight: "5px" }}>Location: {location}</span>
                <span style={{ display: 'inline', borderLeft: '1px solid rgba(0, 0, 0, 0.1)', paddingLeft: '10px', marginRight: "5px" }}>Planted: {planted}</span>
                <span style={{ display: 'inline', borderLeft: '1px solid rgba(0, 0, 0, 0.1)', paddingLeft: '10px', marginRight: "5px" }}>Estimated harvest time: {estimate}</span>
            </div>
            <div>
                <button style={{ marginLeft: '10px' }} onClick={handlePlant}>PLANT</button>
                <button style={{ marginLeft: '10px' }} onClick={handleDelete}>DELETE</button>
                <button style={{ marginLeft: '10px' }} onClick={handleInfo}>INFO</button>
            </div>
        </div>
        <div id="info">
        {plantData ? (
                <PlantInfo info={plantData} />
            ) : (
                <div></div>
            )}
        </div>
        </>
    );
};

PlantComponent.propTypes = {
    name: PropTypes.string.isRequired,
    location: PropTypes.string.isRequired,
    planted: PropTypes.string.isRequired,
    estimate: PropTypes.string.isRequired,
    plantId: PropTypes.number.isRequired
};

export default PlantComponent;