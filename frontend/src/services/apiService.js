import axios from 'axios';
const DB_URL = 'http://localhost:8080';
const COORD_URL = 'http://localhost:8081/api';
const Garden_URL = 'http://localhost:8082';
const weather_URL = 'http://localhost:8083/weather';
//axios.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080';


export const login = async (username, password) => {
    try {
        const response = await axios.post('http://localhost:8080/signin', { username, password });
        sessionStorage.setItem('token', response.data.jwt);
    } catch (error) {
        console.error('Error logging in:', error);
    }
};

export const fetchPlant = async (token, username) => {
    try {
        const prova = await axios.get(COORD_URL + '/getPlantData/' + username, { headers: { Authorization: `Bearer ${token}` } });
        return prova.data;
    } catch (error) {
        console.error('Error fetching plant data:', error);
    }
};

export const SignIn = async (username, password) => {
    try {
        let id = Math.floor(Math.random() * 100);
        const response = await axios.post(COORD_URL + '/users', { id, username, password });
        console.log('Login successful:', response);
        return response;
    }
    catch (error) {
        console.error('Login failed:', error.response ? error.response.data : error.message);
    }
};

export const SearchPlant = async (plantName) => {
    try {
        const response = await axios.get(COORD_URL + '/searchPlant/' + plantName);
        return response.data;
    } catch (error) {
        console.error('Error searching plant:', error);
    }
}

export const addPlantToUser = async (plantName, username, token) => {
    try {
        const response = await axios.post(COORD_URL + '/addPlant', { plantName, username }, { headers: { Authorization: `Bearer ${token}` } });
        return response.data;
    } catch (error) {
        console.error('Error adding plant:', error);
    }
}

export const PlantAPlant = async (plantName, plantId, username, token) => {
    try {
        const response = await axios.put(COORD_URL + '/plant', { plantName, plantId, username }, { headers: { Authorization: `Bearer ${token}` } });
        return response.data;
    } catch (error) {
        console.error('Error planting:', error);
    }
}

export const deletePlant = async (plantId, token) => {
    try {
        const response = await axios.delete(COORD_URL + '/deletePlant/' + plantId, { headers: { Authorization: `Bearer ${token}` } });
        return response.data;
    } catch (error) {
        console.error('Error deleting plant:', error);
    }
}

export const getWeather = async () => {
    let lat = 45.4642;
    let lon = 9.1900;
 navigator.geolocation.getCurrentPosition((position, lat, lon) => {
        lat = position.coords.latitude;
        lon = position.coords.longitude;
        console.log('Latitude:', lat);
        console.log('Longitude:', lon);
    });
    try {
        const response = await axios.get(weather_URL + '?location=' + lat + ',' + lon);
        return response;
    } catch (error) {
        console.error('Error fetching weather data:', error);
    }
}