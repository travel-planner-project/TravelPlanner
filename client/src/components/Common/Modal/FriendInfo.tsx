import Icon from '../Icon'
import styles from './FriendInfo.module.scss'

export type FriendType = {
  profileImageUrl?: string
  userNickname?: string
  email?: string
}

type FriendInfoProps = {
  friend: FriendType
  onChecked: (isChecked: boolean) => void
}

function FriendInfo({ friend, onChecked }: FriendInfoProps) {
  console.log('friend', friend.profileImageUrl)
  return friend.email ? (
    <label htmlFor='friend' className={styles.infoContainer}>
      <input
        id='friend'
        type='checkbox'
        className={styles.checkbox}
        onChange={event => onChecked(event.target.checked)}
      />
      {friend.profileImageUrl ? (
        <img src={friend.profileImageUrl} alt='profile' className={styles.profileImg} />
      ) : (
        <Icon name='profile' size={20} className={styles.profileIcon} />
      )}
      <div>{friend.userNickname}</div>
      <div>{friend.email}</div>
    </label>
  ) : (
    <div className={styles.noInfoMessage}>일치하는 사용자가 없습니다.</div>
  )
}

export default FriendInfo
