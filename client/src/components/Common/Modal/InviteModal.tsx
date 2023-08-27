import { useState } from 'react'
import styles from './Modal.module.scss'
import FriendInfo, { FriendType } from './FriendInfo'
import { GroupMemberListType } from '../../../types/planDetailTypes'
import { searchMember } from '../../../apis/planner'
import { getProfile } from '../../../apis/user'

type InviteModalProp = {
  onClose: () => void
  title: string
  description: string
  placeholder: string
  submitButton: string
  groupMember: GroupMemberListType
  onSubmit: (email: string) => void
}

function InviteModal({
  onClose,
  title,
  description,
  placeholder,
  submitButton,
  groupMember,
  onSubmit,
}: InviteModalProp) {
  const [inputValue, setInputValue] = useState('')
  const [friend, setFriend] = useState<FriendType>({})
  const [isSearchBtnDirty, setIsSearchBtnDirty] = useState(false)

  const handleSearch = async () => {
    const response = await getProfile(1)
    // await searchMember(inputValue)
    // await searchMember(encodeURIComponent(inputValue))
    const { status, data }: { status: number; data: FriendType } = response!

    if (status === 200) {
      setIsSearchBtnDirty(true)
      setFriend({ ...data, isChecked: false })
    }
  }

  const handleSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
    event.preventDefault()
    if (!isSearchBtnDirty) {
      return alert('이메일로 친구 검색을 먼저 해주세요')
    }
    if (!friend.isChecked) {
      return alert('초대하실 친구를 선택 후 초대 버튼을 눌러주세요')
    }
    if (groupMember.find(member => member.email === friend.email)) {
      return alert('이미 그룹에 속해있는 친구는 초대할 수 없습니다.')
    }

    if (friend.email) {
      onSubmit(friend.email)
    }
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
          autoComplete='off'
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
