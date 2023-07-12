import { useSetRecoilState } from 'recoil'
import Icon from './Icon'
import styles from './LogoutBtn.module.scss'
import { userInfo } from '../../store/store'

function LogoutBtn() {
  const resetUserInfo = useSetRecoilState(userInfo)

  return (
    <button type='button' className={styles.logout} onClick={() => resetUserInfo(null)}>
      <Icon name='box-arrow-left' size={24} />
      <span>logout</span>
    </button>
  )
}

export default LogoutBtn
