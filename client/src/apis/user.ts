import axios, { AxiosError } from 'axios'
// import { saveTokenToLocalStorage } from '../utils/tokenHandler'
import { axiosInstance } from './instance'

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
    const response = await axiosInstance.post(`/api/user/login`, {
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
