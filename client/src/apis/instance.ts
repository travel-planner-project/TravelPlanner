import axios, { InternalAxiosRequestConfig } from 'axios'
import { getTokenFromSessionStorage } from '../utils/tokenHandler'

const instanceOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 3000,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
}

const setAccessTokenOnHeader = (config: InternalAxiosRequestConfig) => {
  const accessToken = getTokenFromSessionStorage()
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`
  }
  return config
}

function createAxiosInstance() {
  const instance = axios.create(instanceOptions)
  instance.interceptors.request.use(setAccessTokenOnHeader)
  return instance
}

export const axiosInstance = createAxiosInstance()
