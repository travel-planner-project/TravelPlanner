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

export const checkPassword = async ({ userId, password }: CheckPasswordType) => {
  try {
    const response = await axiosInstance.post('/profile/user/check', { userId, password })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

type EditPasswordType = {
  userId: number
  password: string
}

export const editPassword = async ({ userId, password }: EditPasswordType) => {
  try {
    const response = await axiosInstance.patch('/profile/user/updateInfo', { userId, password })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const deleteUser = async ({ userId, password }: EditPasswordType) => {
  try {
    const response = await axiosInstance.delete('/profile/user/delete', {
      data: { userId, password },
    })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
