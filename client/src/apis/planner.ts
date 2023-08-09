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

type newPlanType = {
  planTitle: string
  isPrivate: boolean
}

export const createNewPlan = async (data: newPlanType) => {
  try {
    const response = await axiosInstance.post(`/planner`, data)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const getPlanDetail = async (id: string) => {
  try {
    const response = await axiosInstance.get(`/planner/${id}`)
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}

export const deletePlan = async (id: number) => {
  try {
    const response = await axiosInstance.delete(`/planner`, {
      data: {
        plannerId: id,
      },
    })
    return response
  } catch (error: unknown) {
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
