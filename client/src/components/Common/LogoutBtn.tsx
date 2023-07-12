import { useResetRecoilState } from 'recoil'
import Icon from './Icon'
import styles from './LogoutBtn.module.scss'
import { userInfo } from '../../store/store'
import useRouter from '../../hooks/useRouter'

function LogoutBtn() {
  const resetUserInfo = useResetRecoilState(userInfo)
  const { routeTo } = useRouter()
  const handleLogout = () => {
    resetUserInfo()
    routeTo(0)
    // 서버에 로그아웃 요청 후 성공하면 새로고침
  }

  return (
    <button type='button' className={styles.logout} onClick={handleLogout}>
      <Icon name='box-arrow-left' size={24} />
      <span>logout</span>
    </button>
  )
}

export default LogoutBtn
