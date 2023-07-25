import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'
import useRouter from '../../hooks/useRouter'
import { getProfile } from '../../apis/user'
import styles from './Profile.module.scss'
import Icon from '../../components/Common/Icon'

function Profile() {
  const { routeTo } = useRouter()
  const { id } = useParams()
  let { userId, userNickname, email, profileImgUrl } = useRecoilValue(userInfo)
  const [isOwner, setIsOwner] = useState(true)

  useEffect(() => {
    if (Number(id) !== userId) {
      setIsOwner(false)
      const response = getProfile(Number(id))
      console.log(response)
      // 응답 받아서 유저인포 재설정
      // 에러 처리
    }
  }, [])

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
        {isOwner ? (
          <>
            <button
              type='button'
              className={styles.blueButton}
              onClick={() => routeTo('/editprofile')}
            >
              프로필 수정
            </button>
            <button
              type='button'
              className={styles.button}
              onClick={() => routeTo('/editpassword')}
            >
              비밀번호 변경
            </button>
            <button type='button' className={styles.button} onClick={() => routeTo('/user/delete')}>
              회원 탈퇴
            </button>
          </>
        ) : null}
      </div>
    </div>
  )
}

export default Profile
