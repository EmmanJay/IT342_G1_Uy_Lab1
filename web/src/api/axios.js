import axios from 'axios'

const API = axios.create({
  baseURL: '/api',
})

// Attach JWT token to every request if present
API.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Auth API calls
export const registerUser = (data) => API.post('/auth/register', data)
export const loginUser = (data) => API.post('/auth/login', data)
export const logoutUser = () => API.post('/auth/logout')
export const getProfile = () => API.get('/user/me')

export default API
