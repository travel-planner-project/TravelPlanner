import axios, { InternalAxiosRequestConfig } from 'axios'
import {
  getTokenFromSessionStorage,
  removeTokenFromSessionStorage,
  saveTokenToSessionStorage,
} from '../utils/tokenHandler'

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
    config.headers.Authorization = accessToken
  }
  return config
}

function createAxiosInstance(options: Options) {
  const instance = axios.create(options)
  instance.interceptors.request.use(setAccessTokenOnHeader)
  instance.interceptors.response.use(
    response => response,
    async error => {
      const { config, response } = error

      if (response.status === 401) {
        try {
          const refreshRes = await axiosInstance.get('/auth/token')
          if (refreshRes.status === 200) {
            const { authorization } = refreshRes.headers
            saveTokenToSessionStorage(authorization)
            config.headers.Authorization = authorization
            return await axios(config)
          }
        } catch (error) {
          sessionStorage.removeItem('userInfo')
          removeTokenFromSessionStorage()
          location.reload()
          return Promise.reject({ status: 403, message: '리프레시 토큰 에러' })
        }
      }
      return Promise.reject(error)
    }
  )

  return instance
}

export const axiosInstance = createAxiosInstance(instanceOptions)
export const axiosInstanceFormData = createAxiosInstance(instanceOptionsFormData)
