import { NavLink } from 'react-router-dom'
import styles from './NavbarProfile.module.scss'
import Icon from './Icon'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'

function NavbarProfile() {
  const userinfo = useRecoilValue(userInfo)

  return (
    <li>
      <NavLink to={userinfo.userId ? '/profile' : '/user/login'}>
        <div className={styles.profileBox}>
          {userinfo.userId && userinfo.profileImgUrl ? (
            <img src={userinfo.profileImgUrl} alt='profile' />
          ) : (
            <Icon name='profile' size={70} />
          )}
        </div>
        <div className={styles.profileName}>
          {userinfo.userId ? userinfo.userNickname : '로그인/회원가입'}
        </div>
      </NavLink>
    </li>
  )
}

export default NavbarProfile
