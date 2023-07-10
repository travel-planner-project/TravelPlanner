import { NavLink } from 'react-router-dom'
import styles from './NavbarProfile.module.scss'
import Icon from './Icon'

const 로그인여부 = false
const profile = 'https://pbs.twimg.com/media/FMKsE-IaAAEFwFk.jpg'

function NavbarProfile() {
  return (
    <li>
      <NavLink to={로그인여부 ? '/profile' : '/user/login'}>
        <div className={styles.profileBox}>
          {profile && 로그인여부 ? (
            <img src={profile} alt='profile' />
          ) : (
            <Icon name='profile' size={70} />
          )}
        </div>
        <div className={styles.profileName}>{로그인여부 ? '유저이름' : '로그인/회원가입'}</div>
      </NavLink>
    </li>
  )
}

export default NavbarProfile
