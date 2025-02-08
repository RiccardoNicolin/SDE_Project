import React, { useState } from 'react';
import { useEffect } from 'react';
import axios from 'axios';
import { getWeather } from '../services/apiService';

const WeatherComp = () => {
        const [weatherInfo, setWeatherInfo] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getWeather();
                setWeatherInfo(response.data);
                console.log('Weather data:', response.data);
            } catch (error) {
                console.error('There was an error making the request!', error);
            }
        };
        fetchData();
    }, []);


    return (
        <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-around', padding: '20px' }}>
            {weatherInfo ? (
                Object.keys(weatherInfo).map((key) => (
                    <div key={key} style={{ border: '1px solid grey', textAlign: 'center' }}>
                        <strong>{weatherInfo[key].date}</strong>
                        <img src={"http:" + weatherInfo[key].icon} alt="weather icon" style={{ display: 'block', margin: '0 auto' }} />
                        <p>Rain: {weatherInfo[key].precipitation}%</p>
                        <div style={{ display: 'flex', justifyContent: 'space-between', padding: '5px', margin: '10px 0' }}>
                            <div style={{ backgroundColor: 'blue', color: 'white', padding: '5px' }}>
                               {weatherInfo[key].min_temp}°C
                            </div>
                            <div style={{ backgroundColor: 'red', color: 'white', padding: '5px' }}>
                                {weatherInfo[key].max_temp}°C
                            </div>
                        </div>
                    </div>
                ))
            ) : (
                <div>No weather</div>
            )}
        </div>
    );
};
export default WeatherComp;