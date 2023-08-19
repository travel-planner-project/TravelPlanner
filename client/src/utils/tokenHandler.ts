const isBrowser = () => typeof window !== 'undefined'

export const saveTokenToSessionStorage = (token: string) => {
  if (isBrowser()) {
    try {
      sessionStorage.setItem('token', token)
    } catch (error) {
      console.error('Error saving accessToken to local storage:', error)
    }
  }
}

export const getTokenFromSessionStorage = () => {
  if (isBrowser()) {
    try {
      return sessionStorage.getItem('token')
    } catch (error) {
      return console.error('Error getting accessToken to local storage:', error)
    }
  }
  return console.error(`it dose not have a browser storage`)
}

export const removeTokenFromSessionStorage = () => {
  if (isBrowser()) {
    try {
      sessionStorage.removeItem('token')
    } catch (error) {
      console.error('Error removing accessToken from local storage:', error)
    }
  }
}
