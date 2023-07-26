import { useResetRecoilState } from 'recoil'
import { userInfo } from '../store/store'
import useRouter from './useRouter'

const useLogout = () => {
  const resetUserInfo = useResetRecoilState(userInfo)
  const { routeTo } = useRouter()

  const logOut = () => {
    resetUserInfo()
    sessionStorage.removeItem('token')
    routeTo(0)
  }

  return logOut
}

export default useLogout
