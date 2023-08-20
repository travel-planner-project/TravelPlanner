import { useResetRecoilState } from 'recoil'
import { userInfo } from '../store/store'
import useRouter from './useRouter'
import { removeTokenFromSessionStorage } from '../utils/tokenHandler'
import { removeRefreshToken } from '../apis/user'

const useLogout = () => {
  const resetUserInfo = useResetRecoilState(userInfo)
  const { routeTo } = useRouter()

  const logOut = async () => {
    const response = await removeRefreshToken()
    if (response?.status === 200) {
      resetUserInfo()
      removeTokenFromSessionStorage()
      routeTo(0)
    } else {
      alert('로그아웃에 실패했습니다. 잠시 후 다시 시도해주세요')
    }
  }

  return logOut
}

export default useLogout
