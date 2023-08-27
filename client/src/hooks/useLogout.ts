import { useResetRecoilState } from 'recoil'
import { userInfo } from '../store/store'
import useRouter from './useRouter'
import { removeTokenFromSessionStorage } from '../utils/tokenHandler'

const useLogout = () => {
  const resetUserInfo = useResetRecoilState(userInfo)
  const { routeTo } = useRouter()

  const logOut = async () => {
    resetUserInfo()
    removeTokenFromSessionStorage()
    routeTo(0)
  }

  return logOut
}

export default useLogout
