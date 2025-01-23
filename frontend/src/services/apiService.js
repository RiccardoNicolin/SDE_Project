import axios from 'axios';
const API_URL = 'http://localhost:8080';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080';


export const login = async (username, password) => {
    try {
        const response =await axios.post('http://localhost:8080/signin', { username, password });
        sessionStorage.setItem('token', response.data.jwt);
    } catch (error) {
        console.error('Error logging in:', error);
    }
};

export const saveData = async (content) => {
    try {
        const response = axios.post(`${API_URL}/DATA`, { content });
        return response.data;
    } catch (error) {
        console.error('Error saving message:', error);
        throw error;
    }
};

export const Strutture = async () => {
    try {
        const response = await axios.get(`${API_URL}/Strutture`);
        //console.log('Data fetched:', response.data);
        return response.data;
    } catch (error) {
        console.error('Errore nel recuperare le strutture:', error);
        throw error;
    }
};

export const Servizi = async () => {
    try {
        const response = await axios.get(`${API_URL}/Servizi`);
        return response.data;
    } catch (error) {
        console.error('Errore nel recuperare i servizi', error);
        throw error;
    }
};

export const delete_request = async (record) => {
    try {
        const response = await axios.post(`${API_URL}/Delete`, record);
        //console.log('Data fetched:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

export const delete_serv_request = async (record) => {
    try {
        const response = await axios.post(`${API_URL}/DeleteServ`, record);
        //console.log('Data fetched:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

export const modifyData = (content) => {
    try {
        const response = axios.post(`${API_URL}/Modifica`, { content });
        return response.data;
    } catch (error) {
        console.error('Error saving message:', error);
        throw error;
    }
};

export const newService = (content) => {
    try {
        const response = axios.post(`${API_URL}/Nuovo_Servizio`, { content });
        return response.data;
    } catch (error) {
        console.error('Error saving new service:', error);
        throw error;
    }
};

export const ModifyService = async (content) => {
    try {
        const response = await axios.post(`${API_URL}/ModificaServ`, { content });
        return response.data;
    } catch (error) {
        console.error('Error modifying service:', error);
        throw error;
    }
};

export const insertModulo_File = async (nome, digitale, servizio) => {
    try {
        const response = await axios.post(`${API_URL}/InsertModuloFile`, { nome, digitale, servizio });
        return response.data

    } catch (error) {
        console.error('Error inserting module:', error);
        throw error;
    }
};

export const insertServ_file = async (id, nome, digitale, struttura) => {
    try {
        const response = await axios.post(`${API_URL}/InserServFile`, { id, nome, digitale, struttura });
        return response.data

    } catch (error) {
        console.error('Error inserting module:', error);
        throw error;
    }
};