import { NavLink } from 'react-router-dom'
import styles from './NavbarProfile.module.scss'
import Icon from '../Icon'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../../store/store'

function NavbarProfile() {
  const { userId, userNickname, profileImgUrl } = useRecoilValue(userInfo)

  return (
    <li>
      <NavLink to={userId ? `/profile/${userId}` : '/user/login'}>
        <div className={styles.profileBox}>
          {userId && profileImgUrl ? (
            <img src={profileImgUrl} alt='profile' />
          ) : (
            <Icon name='profile' size={70} />
          )}
        </div>
        <div className={styles.profileName}>{userId ? userNickname : '로그인/회원가입'}</div>
      </NavLink>
    </li>
  )
}

export default NavbarProfile
