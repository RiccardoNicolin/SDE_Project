import axios from 'axios';
const API_URL = 'http://localhost:8080';
//axios.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080';


export const login = async (username, password) => {
    try {
        const response =await axios.post('http://localhost:8080/signin', { username, password });
        sessionStorage.setItem('token', response.data.jwt);
    } catch (error) {
        console.error('Error logging in:', error);
    }
};

export const fetchPlant = async (token, username) => {
    try {
        const user = await axios.get('http://localhost:8080/api/users/username/' + username, {headers: {Authorization: `Bearer ${token}`}});
        const id = user.data.id;
        const response = await axios.get('http://localhost:8080/api/plants/user/' + id, {headers: {Authorization: `Bearer ${token}`}});
        return response.data;
    } catch (error) {
        console.error('Error fetching plant data:', error);
    }
};