import useRouter from '../../hooks/useRouter'
import styles from './EditProfile.module.scss'
import Icon from '../../components/Common/Icon'

function EditProfile() {
  const { routeTo } = useRouter()
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
        {userInfo.image ? null : (
          <button className={styles.deleteImage} type='button'>
            <Icon name='x-circle-blue' size={24} />
          </button>
        )}
        <button className={styles.cameraBox} type='button'>
          <Icon name='camera' size={24} />
        </button>
      </div>
      <form>
        <input
          className={styles.nickNameInput}
          type='text'
          placeholder={userInfo.nickName}
          maxLength={16}
          required
        />
        <p className={styles.guideText}>닉네임은 공백 없이 16자 이하로 입력 가능합니다.</p>
        <div className={styles.buttonsWrapper}>
          <button className={styles.blueButton} type='submit'>
            확인
          </button>
          <button className={styles.button} type='button' onClick={() => routeTo('/profile')}>
            취소
          </button>
        </div>
      </form>
    </div>
  )
}

export default EditProfile
