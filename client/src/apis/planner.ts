import axios, { AxiosError, CancelToken, Canceler } from 'axios'
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

type optionsType = {
  params: { planTitle: string; page: number }
  cancelToken: CancelToken
}

export const getPlanners = async (options: optionsType) => {
  try {
    const response = await axiosInstance.get(`/feed`, options)
    return response
  } catch (error: unknown) {
    if (axios.isCancel(error)) return
    const axiosError = error as AxiosError
    return axiosError.response
  }
}
