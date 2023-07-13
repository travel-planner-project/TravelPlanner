import axios from 'axios'

const accessToken = import.meta.env.VITE_DEV_ACCESS_TOKEN
const refreshToken = import.meta.env.VITE_DEV_REFRESH_TOKEN

const getCurrentUserPlanner = async (url: string) => {
  const headers = {
    Access_Token: accessToken,
    Refresh_Token: refreshToken,
  }
  try {
    const response = await axios.get(url, { headers })
    return response.data
  } catch (error) {
    console.error('Error fetching planner:', error)
    throw error
  }
}

export default getCurrentUserPlanner
