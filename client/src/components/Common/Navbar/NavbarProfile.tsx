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
        {userId && profileImgUrl ? (
          <img src={profileImgUrl} alt='profile' className={styles.profileImg} />
        ) : (
          <Icon name='profile' size={70} className={styles.profileIcon} />
        )}
        <div className={styles.profileName}>{userId ? userNickname : '로그인/회원가입'}</div>
      </NavLink>
    </li>
  )
}

export default NavbarProfile
