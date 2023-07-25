import styles from './FriendInfo.module.scss'

export type FriendType = {
  id?: number
  profileImg?: string
  userNickname?: string
  email?: string
}

type FriendInfoProps = {
  friend: FriendType
}

function FriendInfo({ friend }: FriendInfoProps) {
  return friend.id ? (
    <label htmlFor='friend' className={styles.infoContainer}>
      <input
        id='friend'
        type='checkbox'
        className={styles.checkbox}
        value={friend.email}
        defaultChecked
        onClick={e => {
          e.preventDefault()
        }}
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
