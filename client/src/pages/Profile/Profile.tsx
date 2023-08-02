import { useState, useEffect } from 'react'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'
import useRouter from '../../hooks/useRouter'
import { getProfile } from '../../apis/user'
import styles from './Profile.module.scss'
import Icon from '../../components/Common/Icon'

function Profile() {
  const { params, routeTo } = useRouter()
  const { id } = params
  const { userId, userNickname, email, profileImgUrl } = useRecoilValue(userInfo)
  const [profileUser, setProfileUser] = useState({
    email: '',
    userNickname: '',
    profileImgUrl: '',
  })
  const [isOwner, setIsOwner] = useState(false)

  useEffect(() => {
    if (Number(id) === userId) {
      setIsOwner(true)
      setProfileUser({
        email: email,
        userNickname: userNickname,
        profileImgUrl: profileImgUrl,
      })
    }
    if (Number(id) !== userId) {
      setIsOwner(false)
      getProfile(Number(id)).then(response => {
        if (response?.status === 200) {
          setProfileUser({
            email: response.data.email,
            userNickname: response.data.userNickname,
            profileImgUrl: response.data.profileImgUrl,
          })
        }
        if (response?.status !== 200) {
          alert('유저 정보를 찾을 수 없습니다.')
          routeTo('/')
        }
      })
    }
  }, [userInfo, id])

  return (
    <div className={styles.entireContainer}>
      <div className={styles.profileContainer}>
        <div className={styles.profileBox}>
          {profileUser.profileImgUrl ? (
            <img src={profileUser.profileImgUrl} alt='profile' />
          ) : (
            <Icon name='profile' size={64} />
          )}
        </div>
        <div className={styles.profileInfo}>
          <div className={styles.profileName}>{profileUser.userNickname}</div>
          <div className={styles.profileEmail}>{profileUser.email}</div>
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
