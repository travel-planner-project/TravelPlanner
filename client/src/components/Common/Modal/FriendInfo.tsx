import styles from './FriendInfo.module.scss'

export type FriendType = {
  id?: number
  profileImg?: string
  userNickname?: string
  email?: string
  isChecked?: boolean
}

type FriendInfoProps = {
  friend: FriendType
  onChecked: (isChecked: boolean) => void
  // setFriend: React.Dispatch<React.SetStateAction<FriendType>>
}

function FriendInfo({ friend, onChecked }: FriendInfoProps) {
  return friend.id ? (
    <label htmlFor='friend' className={styles.infoContainer}>
      <input
        id='friend'
        type='checkbox'
        className={styles.checkbox}
        onChange={event => onChecked(event.target.checked)}
      />
      <img src={friend.profileImg} alt='profile' className={styles.profile} />
      <div>{friend.userNickname}</div>
      <div>{friend.email}</div>
    </label>
  ) : (
    <div className={styles.noInfoMessage}>일치하는 사용자가 없습니다.</div>
  )
}

export default FriendInfo
