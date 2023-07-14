import axios, { InternalAxiosRequestConfig } from 'axios'
import { getTokenFromLocalStorage } from '../utils/tokenHandler'

const instanceOptions = {
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 3000,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
}

const setAccessTokenOnHeader = (config: InternalAxiosRequestConfig) => {
  // eslint-disable-next-line no-param-reassign
  config.headers.Authorization = getTokenFromLocalStorage('accessToken') || ''
  return config
}

function createAxiosInstance() {
  const instance = axios.create(instanceOptions)
  instance.interceptors.request.use(setAccessTokenOnHeader)
  return instance
}

export const axiosInstance = createAxiosInstance()

// function createAuthAxiosInstance() {
//   const instance = axios.create(instanceOptions)
//   instance.interceptors.request.use(setAccessTokenOnHeader)
//   instance.interceptors.response.use(
//     response => response,
//     async error => {
//       const { config, response } = error

//       if (response.status === 401) {
//         try {
//           const refreshResponse = await axiosInstance.get('')
//           // refresh token 으로 access 토큰 갱신 요청해서
//           // 새로운 access token헤더에 설정 필요
//         } catch (err) {
//           return Promise.reject(err)
//         }
//       }
//       return Promise.reject(error)
//     }
//   )
// }
