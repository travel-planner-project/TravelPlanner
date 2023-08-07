import { AxiosError } from 'axios'
import { axiosInstance } from './instance'

export const getCurrentUserPlanner = async (email: string) => {
  try {
    const response = await axiosInstance.get(`/planner?email=${email}`)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const createNewPlan = async (email: string) => {
  try {
    const response = await axiosInstance.post(`/planner?email=${email}`)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
