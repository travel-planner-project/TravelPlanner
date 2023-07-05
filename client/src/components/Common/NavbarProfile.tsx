import { NavLink } from 'react-router-dom'
import styles from './NavbarProfile.module.scss'

const 로그인여부 = false
const profile = 'https://pbs.twimg.com/media/FMKsE-IaAAEFwFk.jpg'
const noProfile = 'https://static-00.iconduck.com/assets.00/person-icon-476x512-hr6biidg.png'

function NavbarProfile() {
  return (
    <li>
      <NavLink to={로그인여부 ? '/profile' : '/user/login'}>
        <div className={styles.profileBox}>
          <img src={로그인여부 ? profile : noProfile} alt='profile' />
        </div>
        <div className={styles.profileName}>{로그인여부 ? '유저이름' : '로그인/회원가입'}</div>
      </NavLink>
    </li>
  )
}

export default NavbarProfile
