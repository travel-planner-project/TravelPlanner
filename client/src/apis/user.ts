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
    const { authorization } = response.headers
    saveTokenToSessionStorage(authorization)
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

type PasswordType = {
  password: string
}

export const checkPassword = async ({ password }: PasswordType) => {
  try {
    const response = await axiosInstance.post('/profile/user/check', { password })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const editPassword = async ({ password }: PasswordType) => {
  try {
    const response = await axiosInstance.patch('/profile/user/updateInfo', { password })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const changePassword = async (newPassword: string, token: string) => {
  try {
    const response = await axiosInstance.post(`/password/change?tempToken=${token}`, {
      newPassword,
    })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const deleteUser = async ({ password }: PasswordType) => {
  try {
    const response = await axiosInstance.delete('/profile/user/delete', {
      data: { password },
    })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const refreshAccessToken = async () => {
  try {
    const response = await axiosInstance.get('/auth/token')
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const findPassword = async (email: FormDataEntryValue | null) => {
  try {
    const response = await axiosInstance.post('/password/forgot', { email })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
