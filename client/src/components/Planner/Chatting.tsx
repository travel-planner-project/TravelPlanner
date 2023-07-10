import { useState, useEffect, useRef } from 'react'
import * as StompJs from '@stomp/stompjs'
import styles from './Chatting.module.scss'
import Icon from '../Common/Icon'

type Chat = {
  userId: number
  userNickname: string
  // userProfile: string
  message: string
}

function Chatting() {
  // 임의의 더미 데이터
  // const chatList = [
  //   {
  //     userId: 11,
  //     userNickname: '가나다라마바사',
  //     message: '안녕',
  //   },
  //   {
  //     userId: 12,
  //     userNickname: '얍',
  //     message: '이번엔 어딜가볼까요? 부산? 제주도?',
  //   },
  //   {
  //     userId: 13,
  //     userNickname: '나나',
  //     message:
  //       '이거는 이래서 좋고, 저거는 저래서 좋으니 이렇게 하는 게 어떤 부분에서 괜찮을 것 같아요. 여러분들 생각은 어떠신가요???',
  //   },
  // ]

  const plannerId = 1 // 임시로 1로 설정. useParams()로 받아오는 게 좋을 듯.
  const userId = 13 // 임시로 13으로 설정. 로그인 기능 구현 후, 로그인한 유저의 id로 설정.
  const [chatList, setChatList] = useState<Chat[]>([])
  const [newChat, setNewChat] = useState<string>('')

  const clientRef = useRef<StompJs.Client | null>(null)
  const scrollContainerRef = useRef(null)

  // 리스트 갱신 시 스크롤 맨 아래로
  useEffect(() => {
    const scrollContainer = scrollContainerRef.current as HTMLElement | null
    if (!scrollContainer) {
      return
    }
    const { scrollHeight } = scrollContainer
    scrollContainer.scrollTo(0, scrollHeight)
  }, [chatList])

  useEffect(() => {
    // 1. 클라이언트 객체 생성
    const client = new StompJs.Client({
      // brokerURL 속성이 존재하는 경우, 해당 URL을 기반으로 새로운 WebSocket을 생성한다.
      brokerURL: import.meta.env.VITE_BROKER_URL,

      // 연결에 사용될 로그인 정보 -> 토큰..? 필요할지 모르겠음.
      // connectHeaders: {
      //   Authorization: `Bearer ${token}`,
      // },

      // 디버깅용
      // debug(str) {
      //   console.log(str)
      // },

      // 재연결 시도 사이의 지연 시간: 5초
      reconnectDelay: 5000,
      // 수신 및 송신 하트비트 주기: 4초
      // 하트비트란 정기적으로 보내지는 작은 메시지로, 상대방이 연결되어 있는지 확인하는 용도로 사용된다.
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    })

    // 3. 클라이언트가 메시지 브로커에 연결(커넥트)되었을 때 호출되는 함수
    client.onConnect = function (frame) {
      // console.log(`Connected: ${frame}`)
      // 메시지 받는 함수
      const callback = function (message: any) {
        if (message.body) {
          const body = JSON.parse(message.body)
          if (body.type === 'chat') {
            setChatList(prev => [...prev, body.msg])
          }
        }
      }
      // 구독하기
      client.subscribe(`/sub/planner-message/${plannerId}`, callback)
    }

    // 브로커에서 에러가 발생했을 때 호출되는 함수
    client.onStompError = function (frame) {
      console.log(`Broker reported error: ${frame.headers.message}`)
      console.log(`Additional details: ${frame.body}`)
    }

    // 2. 클라이언트 활성화 -> 설정된 주소로 연결을 시도하고, 연결이 성공하면 onConnect 콜백 함수 호출!
    client.activate()
    clientRef.current = client

    // 컴포넌트가 언마운트될 때 클라이언트 비활성화
    return () => {
      client.deactivate()
    }
  }, [])

  const handleChange = (event: any) => {
    setNewChat(event.target.value)
  }

  const handleSubmit = (event: any) => {
    event.preventDefault()
    if (newChat.trim() !== '') {
      const newChatObj = { message: newChat }
      const msg = JSON.stringify(newChatObj)
      // 4. 메시지 보내기(퍼블리시)
      if (clientRef.current) {
        clientRef.current.publish({ destination: `/pub/chat/${plannerId}`, body: msg })
      }
    }
    setNewChat('')
    event.target.reset()
  }

  return (
    <div className={styles.background}>
      <div className={styles.chatContainer}>
        <div className={styles.chatHeader}>
          <h1>그룹 채팅</h1>
          <Icon name='x' size={18} />
        </div>
        <div className={styles.chatBody} ref={scrollContainerRef}>
          {chatList.map(chat => (
            <div className={styles.message} key={crypto.randomUUID()}>
              <span
                className={`${
                  chat.userId === userId ? styles.loggedInUser : styles.notLoggedInUser
                }`}
              >
                {chat.userNickname}:{' '}
              </span>
              <span className={styles.text}>{chat.message}</span>
            </div>
          ))}
        </div>
        <form className={styles.chatFooter} onSubmit={handleSubmit}>
          <input
            type='text'
            className={styles.chatInput}
            placeholder='메시지를 입력하세요'
            value={newChat}
            onChange={handleChange}
          />
          <button type='submit' className={styles.sendButton}>
            send
          </button>
        </form>
      </div>
    </div>
  )
}

export default Chatting
