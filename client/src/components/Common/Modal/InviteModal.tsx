import { useState } from 'react'
import styles from './Modal.module.scss'
import FriendInfo, { FriendType } from './FriendInfo'
import { ModalSubmitDataType } from '../../../store/store'

// 가짜 Friend 데이터
const Friend = {
  id: 123,
  profileImg: 'https://i.ytimg.com/vi/y9bWhYGvBlk/maxresdefault.jpg',
  userNickname: '닉네임',
  email: 'test123@naver.com',
}

type InviteModalProp = {
  onClose: () => void
  title: string
  description: string
  placeholder: string
  submitButton: string
  onSubmit: (modalSubmitData: ModalSubmitDataType) => void
}

function InviteModal({
  onClose,
  title,
  description,
  placeholder,
  submitButton,
  onSubmit,
}: InviteModalProp) {
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
    //    setFriend( {...data, isChecked: false } )
    setFriend({ ...Friend, isChecked: false })
    // }
  }

  const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault()
    if (!isSearchBtnDirty) {
      return alert('이메일로 친구 검색을 먼저 해주세요')
    }

    if (!friend.isChecked) {
      return alert('초대하실 친구를 선택 후 초대 버튼을 눌러주세요')
    }

    // const { status, data } =  await onSubmit(friend.email)
    // if(status !== 200) { return alert('친구 초대에 실패했습니다. 잠시 후 다시 시도해주세요.') }

    onClose()
  }

  return (
    <form className={styles.form}>
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
        <button type='button' className={styles.searchBtn} onClick={handleSearch}>
          검색
        </button>
      </div>
      {isSearchBtnDirty && (
        <FriendInfo
          friend={friend}
          onChecked={(isChecked: boolean) => setFriend({ ...friend, isChecked })}
        />
      )}
      <div className={styles.btnBox}>
        <button type='submit' className={styles.submitBtn} onClick={handleSubmit}>
          {submitButton}
        </button>
        <button type='button' className={styles.cancelBtn} onClick={onClose}>
          취소
        </button>
      </div>
    </form>
  )
}

export default InviteModal
