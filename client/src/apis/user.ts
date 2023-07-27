import axios, { AxiosError } from 'axios'
import { saveTokenToSessionStorage } from '../utils/tokenHandler'
import { axiosInstance, axiosInstanceFormData } from './instance'

type SignUpType = {
  email: string
  userNickname: string
  password: string
}

export const signUp = async ({ email, password, userNickname }: SignUpType) => {
  try {
    const response = await axios.post(`${import.meta.env.VITE_BASE_URL}/auth/signup`, {
      email,
      password,
      userNickname,
    })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

type SignInType = {
  email: FormDataEntryValue | null
  password: FormDataEntryValue | null
}

export const signIn = async ({ email, password }: SignInType) => {
  try {
    const response = await axiosInstance.post(`/auth/login`, {
      email,
      password,
    })
    saveTokenToSessionStorage(response.data.token)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const getProfile = async (id: number) => {
  try {
    const response = await axiosInstance.get(`/profile?userId=${id}`)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const editProfile = async (formData: FormData) => {
  try {
    const response = await axiosInstanceFormData.patch('/profile', formData)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

type CheckPasswordType = {
  userId: number
  password: string
}

export const CheckPassword = async ({ userId, password }: CheckPasswordType) => {
  try {
    const response = await axiosInstance.post('/profile/user/check', { userId, password })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
