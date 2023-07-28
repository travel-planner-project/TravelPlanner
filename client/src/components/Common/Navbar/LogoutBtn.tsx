import Icon from '../Icon'
import styles from './LogoutBtn.module.scss'
import useLogout from '../../../hooks/useLogout'

function LogoutBtn() {
  const logout = useLogout()

  return (
    <button type='button' className={styles.logout} onClick={logout}>
      <Icon name='box-arrow-left' size={24} />
      <span>logout</span>
    </button>
  )
}

export default LogoutBtn
