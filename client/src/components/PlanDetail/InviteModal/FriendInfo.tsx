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
  return (
    <label htmlFor='friend' className={styles.infoContainer}>
      <input
        id='friend'
        type='checkbox'
        className={styles.checkbox}
        value={friend.email}
        defaultChecked
        required
      />
      <img src={friend.profileImg} alt='profile' className={styles.profile} />
      <div>{friend.userNickname}</div>
      <div>{friend.email}</div>
    </label>
  )
}

export default FriendInfo
