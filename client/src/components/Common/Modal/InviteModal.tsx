import { useState } from 'react'
import styles from './Modal.module.scss'
import FriendInfo, { FriendType } from './FriendInfo'

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
  onSubmit: (input: string) => void
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
    //    setFriend( data )
    setFriend(Friend)
    // }
  }

  const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault()
    if (!isSearchBtnDirty) {
      // 검색 버튼을 안 누르고 초대버튼을 누른 경우
      return alert('검색 버튼을 먼저 클릭해주세요')
    }

    // const { status, data } =  await onSubmit(inputValue)
    // if(status !== 200) { return alert('친구 초대에 실패했습니다. 잠시 후 다시 시도해주세요.') }

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
        <button type='button' className={styles.searchBtn} onClick={handleSearch}>
          검색
        </button>
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

export default InviteModal
