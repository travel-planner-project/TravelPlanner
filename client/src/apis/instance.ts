import axios, { InternalAxiosRequestConfig } from 'axios'
import { getTokenFromSessionStorage } from '../utils/tokenHandler'

type Options = {
  baseURL: string
  timeout: number
  headers: { 'Content-Type': string }
  withCredentials: boolean
}

const instanceOptions: Options = {
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 3000,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
}

const instanceOptionsFormData: Options = {
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 3000,
  headers: { 'Content-Type': 'multipart/form-data' },
  withCredentials: true,
}

const setAccessTokenOnHeader = (config: InternalAxiosRequestConfig) => {
  const accessToken = getTokenFromSessionStorage()
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`
  }
  return config
}

function createAxiosInstance(options: Options) {
  const instance = axios.create(options)
  instance.interceptors.request.use(setAccessTokenOnHeader)
  return instance
}

export const axiosInstance = createAxiosInstance(instanceOptions)
export const axiosInstanceFormData = createAxiosInstance(instanceOptionsFormData)
