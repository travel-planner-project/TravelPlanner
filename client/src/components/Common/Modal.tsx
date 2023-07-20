import { useEffect, useState } from 'react'
import styles from './Modal.module.scss'
import FriendInfo, { FriendType } from '../PlanDetail/InviteModal/FriendInfo'

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
  isSearchBtn = false,
}: ModalContentProp) {
  const [inputValue, setInputValue] = useState('')
  const [friend, setFriend] = useState<FriendType>({})

  const handleSearch = () => {
    console.log('input 입력값:', inputValue, '을 서버로 보내고 받은 응답으로 friend 상태 업데이트')
    setFriend(Friend)
  }

  return (
    <>
      <div className={styles.title}>{title}</div>
      <div className={styles.description}>{description}</div>
      <div className={styles.inputSearchBox}>
        <input
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
      {isSearchBtn && friend.id && <FriendInfo friend={friend} />}
      <div className={styles.btnBox}>
        <button type='button' className={styles.submitBtn}>
          {submitButton}
        </button>
        <button type='button' className={styles.cancelBtn} onClick={onClose}>
          취소
        </button>
      </div>
    </>
  )
}

type ModalProp = ModalContentProp & {
  isOpen: boolean
}

function Modal({ isOpen, onClose, ...modal }: ModalProp) {
  const handleEscBtn = (event: KeyboardEvent) => {
    const key = event.key || event.keyCode
    if (key === 'Escape' || key === 27) {
      onClose()
    }
  }

  useEffect(() => {
    document.addEventListener('keydown', handleEscBtn)
    return () => document.removeEventListener('keydown', handleEscBtn)
  }, [])

  return (
    isOpen && (
      <div className={styles.background} onClick={onClose} role='presentation'>
        <div
          className={styles.wrapper}
          onClick={(e: React.MouseEvent) => e.stopPropagation()}
          role='presentation'
        >
          <div className={styles.content}>
            <ModalContent onClose={onClose} {...modal} />
          </div>
        </div>
      </div>
    )
  )
}

export default Modal
