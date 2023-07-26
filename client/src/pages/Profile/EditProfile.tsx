import { useState } from 'react'
import { useRecoilValue, useSetRecoilState } from 'recoil'
import { userInfo } from '../../store/store'
import useRouter from '../../hooks/useRouter'
import { editProfile } from '../../apis/user'
import styles from './EditProfile.module.scss'
import Icon from '../../components/Common/Icon'

function EditProfile() {
  const { routeTo } = useRouter()
  const { userId, email, userNickname, profileImgUrl } = useRecoilValue(userInfo)
  const setUserInfo = useSetRecoilState(userInfo)
  const [editNickname, setEditNickname] = useState(userNickname)
  const [selectedImage, setSelectedImage] = useState<File | string>(profileImgUrl)
  const [previewImage, setPreviewImage] = useState(profileImgUrl)

  // 미리보기 이미지 업데이트 함수
  const updatePreviewImage = (file: File | null) => {
    if (file) {
      const reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onloadend = () => {
        setPreviewImage(reader.result as string)
      }
    } else {
      setPreviewImage('')
    }
  }

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setSelectedImage(event.target.files[0])
      updatePreviewImage(event.target.files[0])
    }
  }

  const handleDeleteImage = () => {
    setSelectedImage('')
    updatePreviewImage(null)
  }

  const handleNicknameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEditNickname(event.target.value)
  }

  const handleKeyDown = (event: { key: string; preventDefault: () => void }) => {
    // 만약 눌린 키가 스페이스바라면 이벤트를 막는다.
    if (event.key === ' ') {
      event.preventDefault()
    }
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData()
    const nickNameBlob = new Blob(
      [
        JSON.stringify({
          userId: userId,
          userNickname: editNickname,
          existProfileImgUrl: profileImgUrl,
        }),
      ],
      {
        type: 'application/json',
      }
    )
    formData.append('profileUpdateRequest', nickNameBlob)
    if (typeof selectedImage === 'string') {
      const ImageBlob = new Blob([selectedImage], {
        type: 'application/json',
      })
      formData.append('profileImg', ImageBlob)
    } else {
      formData.append('profileImg', selectedImage)
    }
    console.log(selectedImage)
    editProfile(formData).then(response => {
      if (response?.status === 200) {
        alert('회원정보가 변경되었습니다.')
        setUserInfo({
          userId: userId,
          userNickname: response.data.userNickname,
          email: email,
          profileImgUrl: response.data.profileImgUrl,
        })
        console.log(response.data)
        routeTo(`/profile/${userId}`)
      }
      if (response?.status !== 200) {
        alert('회원정보 변경에 실패했습니다. 잠시 후 다시 시도해주세요.')
      }
    })
  }

  return (
    <form className={styles.entireContainer} onSubmit={handleSubmit}>
      <div className={styles.profileContainer}>
        <div className={styles.profileBox}>
          {previewImage ? (
            <>
              <img src={previewImage} alt='profile' />
              <button className={styles.deleteImage} type='button' onClick={handleDeleteImage}>
                <Icon name='x-circle-blue' size={24} />
              </button>
            </>
          ) : (
            <Icon name='profile' size={64} />
          )}
        </div>
        <label className={styles.cameraBox} htmlFor='imageInput'>
          <Icon name='camera' size={24} />
        </label>
        <input
          type='file'
          id='imageInput'
          accept='image/*'
          onChange={handleImageChange}
          style={{ display: 'none' }}
        />
      </div>
      <input
        className={styles.nickNameInput}
        type='text'
        maxLength={16}
        placeholder={userNickname}
        value={editNickname}
        onChange={handleNicknameChange}
        onKeyDown={handleKeyDown}
        required
      />
      <p className={styles.guideText}>닉네임은 공백 없이 16자 이하로 입력 가능합니다.</p>
      <div className={styles.buttonsWrapper}>
        <button className={styles.blueButton} type='submit'>
          확인
        </button>
        <button className={styles.button} type='button' onClick={() => routeTo(-1)}>
          취소
        </button>
      </div>
    </form>
  )
}

export default EditProfile
