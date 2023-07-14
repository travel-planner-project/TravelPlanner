const isBrowser = () => typeof window !== 'undefined'

export const saveTokenToLocalStorage = (access_token: string, refresh_token: string) => {
  if (isBrowser()) {
    try {
      localStorage.setItem('accessToken', access_token)
      localStorage.setItem('refreshToken', refresh_token)
    } catch (error) {
      console.error('Error saving accessToken to local storage:', error)
    }
  }
}

export const getTokenFromLocalStorage = (type: 'accessToken' | 'refreshToken') => {
  if (isBrowser()) {
    try {
      if (type === 'accessToken') {
        return localStorage.getItem('accessToken')
      }
      if (type === 'refreshToken') {
        return localStorage.getItem('refreshToken')
      }
      return console.error('wrong local storage key')
    } catch (error) {
      return console.error('Error getting accessToken to local storage:', error)
    }
  }
  return console.error(`it dose not have a browser storage`)
}

export const removeTokenFrom = () => {
  if (isBrowser()) {
    try {
      localStorage.remove('accessToken')
      localStorage.remove('refreshToken')
    } catch (error) {
      console.error('Error removing accessToken from local storage:', error)
    }
  }
}
