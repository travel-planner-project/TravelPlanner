import { useEffect, useState } from 'react'
import styles from './Modal.module.scss'
import FriendInfo, { FriendType } from '../PlanDetail/InviteModal/FriendInfo'
import useModal from '../../hooks/useModal'

// 가짜 Friend 데이터
const Friend = {
  id: 123,
  profileImg: 'https://i.ytimg.com/vi/y9bWhYGvBlk/maxresdefault.jpg',
  userNickname: '닉네임',
  email: 'test123@naver.com',
}

type ModalContentProp = {
  onClose: () => void
  title: string
  description: string
  placeholder: string
  submitButton: string
  isSearchBtn?: boolean
}

function ModalContent({
  onClose,
  title,
  description,
  placeholder,
  submitButton,
  onSubmit,
  isSearchBtn = false,
}: ModalContentProp) {
  const [inputValue, setInputValue] = useState('')
  const [friend, setFriend] = useState<FriendType>({})
  const [isSearchBtnDirty, setIsSearchBtnDirty] = useState(false)

  const handleSearch = async () => {
    console.log('input 입력값:', inputValue, '을 서버로 보내고 받은 응답으로 friend 상태 업데이트')

    // 친구 검색 api 에 inputValue state 를 넣어서 request 전송하고
    // api의 응답 데이터를 setFriend(response.data) 로 업데이트

    // const { status, data } = await searchFriend( inputValue )
    // if(status === 200){
    setIsSearchBtnDirty(true)
    //    setFriend( data )
    setFriend(Friend)
    // }
  }

  const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault()
    if (isSearchBtn && friend.id) {
      // 친구 초대 모달인 경우
      // friend state를 submit 하는 api request 에 넣어서 전송
      // await onSubmit(friend.email)
    }

    // 여행 추가 모달인 경우 & 친구 초대 모달이지만 검색된 유저가 없는 경우
    // const { status, data } =  await onSubmit(inputValue)
    // if(status !== 200) { alert(data.message) }

    onClose()
  }

  return (
    <>
      <label htmlFor={title} className={styles.title}>
        {title}
      </label>
      <div className={styles.description}>{description}</div>
      <div className={styles.inputSearchBox}>
        <input
          id={title}
          className={styles.input}
          placeholder={placeholder}
          value={inputValue}
          onChange={event => setInputValue(event.target.value)}
        />
        {isSearchBtn && (
          <button type='button' className={styles.searchBtn} onClick={handleSearch}>
            검색
          </button>
        )}
      </div>
      {isSearchBtnDirty && <FriendInfo friend={friend} />}
      <div className={styles.btnBox}>
        <button type='submit' className={styles.submitBtn} onClick={handleSubmit}>
          {submitButton}
        </button>
        <button type='button' className={styles.cancelBtn} onClick={onClose}>
          취소
        </button>
      </div>
    </>
  )
}

function Modal() {
  const { modalData, closeModal } = useModal()

  const handleEscBtn = (event: KeyboardEvent) => {
    const key = event.key || event.keyCode
    if (key === 'Escape' || key === 27) {
      closeModal()
    }
  }

  useEffect(() => {
    document.addEventListener('keydown', handleEscBtn)
    return () => document.removeEventListener('keydown', handleEscBtn)
  }, [])

  return (
    modalData.isOpen && (
      <div className={styles.background} onClick={closeModal} role='presentation'>
        <div
          className={styles.wrapper}
          onClick={(e: React.MouseEvent) => e.stopPropagation()}
          role='presentation'
        >
          <form className={styles.content}>
            <ModalContent onClose={closeModal} {...modalData} />
          </form>
        </div>
      </div>
    )
  )
}

export default Modal
