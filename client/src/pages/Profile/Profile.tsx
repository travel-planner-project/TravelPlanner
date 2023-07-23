import { useNavigate } from 'react-router-dom'
import styles from './Profile.module.scss'
import Icon from '../../components/Common/Icon'

function Profile() {
  const navigate = useNavigate()

  // 리코일에 있는 유저 정보 받아오기 (임의의 더미 데이터로 대체)
  // -> 실제 데이터 받으면 구조분해할당해서 사용할 예정
  const userInfo = {
    nickName: '시은',
    email: 'seeeun@gmail.com',
    image: null,
  }

  return (
    <div className={styles.entireContainer}>
      <div className={styles.profileContainer}>
        <div className={styles.profileBox}>
          {userInfo.image ? (
            <img src={userInfo.image} alt='profile' />
          ) : (
            <Icon name='profile' size={64} />
          )}
        </div>
        <div className={styles.profileInfo}>
          <div className={styles.profileName}>{userInfo.nickName}</div>
          <div className={styles.profileEmail}>{userInfo.email}</div>
        </div>
        <button
          type='button'
          className={styles.blueButton}
          onClick={() => navigate('/editprofile')}
        >
          프로필 수정
        </button>
        <button type='button' className={styles.button} onClick={() => navigate('/editpassword')}>
          비밀번호 변경
        </button>
        <button type='button' className={styles.button} onClick={() => navigate('/user/delete')}>
          회원 탈퇴
        </button>
      </div>
    </div>
  )
}

export default Profile
