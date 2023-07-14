import axios, { AxiosError } from 'axios'
import { FormValueType } from '../types/SignUp'
import { saveTokenToLocalStorage } from '../utils/tokenHandler'
import { axiosInstance } from './instance'

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
    const response = await axiosInstance.post(`${import.meta.env.VITE_BASE_URL}/api/user/login`, {
      email,
      password,
    })
    console.log('header', response.headers)
    // const { Access_token: AccessToken, Refresh_token: RefreshToken } = response.headers

    // saveTokenToLocalStorage(AccessToken, RefreshToken)
    // console.log('AT', AccessToken)
    // console.log('RT', RefreshToken)
    return response.status
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response?.status
  }
}
