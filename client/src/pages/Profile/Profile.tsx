import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'
import useRouter from '../../hooks/useRouter'
import styles from './Profile.module.scss'
import Icon from '../../components/Common/Icon'

function Profile() {
  const { routeTo } = useRouter()
  const { userId, userNickname, email, profileImgUrl } = useRecoilValue(userInfo)

  return (
    <div className={styles.entireContainer}>
      <div className={styles.profileContainer}>
        <div className={styles.profileBox}>
          {profileImgUrl ? (
            <img src={profileImgUrl} alt='profile' />
          ) : (
            <Icon name='profile' size={64} />
          )}
        </div>
        <div className={styles.profileInfo}>
          <div className={styles.profileName}>{userNickname}</div>
          <div className={styles.profileEmail}>{email}</div>
        </div>
        <button type='button' className={styles.blueButton} onClick={() => routeTo('/editprofile')}>
          프로필 수정
        </button>
        <button type='button' className={styles.button} onClick={() => routeTo('/editpassword')}>
          비밀번호 변경
        </button>
        <button type='button' className={styles.button} onClick={() => routeTo('/user/delete')}>
          회원 탈퇴
        </button>
      </div>
    </div>
  )
}

export default Profile
