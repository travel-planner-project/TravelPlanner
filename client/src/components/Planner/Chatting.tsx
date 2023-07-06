import styles from './Chatting.module.scss'
import Icon from '../common/Icon'

function Chatting() {
  // 임의의 더미 데이터
  const messages = [
    {
      id: 1,
      nickName: '가나다라마바사',
      chatMessage: '안녕',
    },
    {
      id: 2,
      nickName: '얍',
      chatMessage: '이번엔 어딜가볼까요? 부산? 제주도?',
    },
    {
      id: 3,
      nickName: '나나',
      chatMessage:
        '이거는 이래서 좋고, 저거는 저래서 좋으니 이렇게 하는 게 어떤 부분에서 괜찮을 것 같아요. 여러분들 생각은 어떠신가요???',
    },
  ]

  return (
    <div className={styles.background}>
      <div className={styles.chatContainer}>
        <div className={styles.chatHeader}>
          <h1>그룹 채팅</h1>
          <Icon name='x' size={18} />
        </div>
        <div className={styles.chatBody}>
          {messages.map(message => (
            <div className={styles.message} key={message.id}>
              <span className={styles.sender}>{message.nickName}: </span>
              <span className={styles.text}>{message.chatMessage}</span>
            </div>
          ))}
        </div>
        <div className={styles.chatFooter}>
          <input type='text' className={styles.chatInput} placeholder='메시지를 입력하세요' />
          <button type='button' className={styles.sendButton}>
            send
          </button>
        </div>
      </div>
    </div>
  )
}

export default Chatting
