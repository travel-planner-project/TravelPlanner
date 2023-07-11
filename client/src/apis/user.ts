import axios, { AxiosError } from 'axios'
import { FormValueType } from '../types/SignUp'

type SignInType = {
  email: FormDataEntryValue | null
  password: FormDataEntryValue | null
}

export const signUp = async ({ email, password, passwordCheck, userNickname }: FormValueType) => {
  try {
    const response = await axios.post(`${import.meta.env.VITE_BASE_URL}/api/user/register`, {
      email,
      password,
      passwordCheck,
      userNickname,
    })
    return response.status
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response?.status
  }
}

export const signIn = async ({ email, password }: SignInType) => {
  try {
    const response = await axios.post(`${import.meta.env.VITE_BASE_URL}/api/user/login`, {
      email,
      password,
    })
    return response.status
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response?.status
  }
}
