import axios, { AxiosError } from 'axios'
import { FormValueType } from '../types/SignUp'

type SignInType = {
  email: string
  password: string
}

const BaseURL = 'http://61.82.175.32:8080/api/user'

export const signUp = async ({ email, password, passwordCheck, userNickname }: FormValueType) => {
  try {
    const response = await axios.post(`${BaseURL}/register`, {
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
    const response = await axios.post(`${BaseURL}/login`, { email, password })
    return response.status
  } catch (error) {
    console.log('로그인 error', error)
    return 'error'
  }
}
