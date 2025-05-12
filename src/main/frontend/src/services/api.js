import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/transfers',
  headers: { 'Content-Type': 'application/json' }
});

export const fetchTransfers = () => api.get();
export const createTransfer = (data) => api.post('', data);
