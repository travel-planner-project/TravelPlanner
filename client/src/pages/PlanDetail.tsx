import { useState, useEffect, useRef } from 'react'
import * as StompJs from '@stomp/stompjs'
import Icon from '../components/Common/Icon'
import Element from '../components/PlanDetail/PlanElement/Element'
import styles from './PlanDetail.module.scss'
import Chatting from '../components/Planner/Chatting'
import { Chat } from '../types/PlanDetail'

type PlanDetailProps = {
  userId: number
  chatModal: boolean
  chatList: Chat[]
  newChat: string
  onChatModalTrue: () => void
  onChatModalFalse: () => void
  onChatChange: (event: any) => void
  onChatSubmit: (event: any) => void
}

function PlanDetailView({
  userId,
  chatModal,
  chatList,
  newChat,
  onChatModalTrue,
  onChatModalFalse,
  onChatChange,
  onChatSubmit,
}: PlanDetailProps) {
  return (
    <div className={styles.planContainer}>
      <div className={styles.planHeader}>
        <div className={styles.planTitle}>제주 여행</div>
        <div className={styles.planPeriodBox}>
          <div className={styles.planPeriod}>
            <Icon name='calendar' size={16} />
            <div className={styles.dateBox}>
              <div className={styles.startDate}>2023-07-14</div>
              <span> ~ </span>
              <div className={styles.endDate}>2023-07-16</div>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.planBody}>
        <div className={styles.userList}>
          <div className={styles.users}>
            <div className={styles.user}>
              <div className={styles.userProfileBox}>
                {/* <img src='' alt='' /> */}
                <Icon name='profile' size={42} />
              </div>
              <div className={styles.userName}>시은</div>
            </div>
            <div className={styles.user}>
              <div className={styles.userProfileBox}>
                {/* <img src='' alt='' /> */}
                <Icon name='profile' size={42} />
              </div>
              <div className={styles.userName}>설화</div>
            </div>
            <div className={styles.user}>
              <div className={styles.userProfileBox}>
                {/* <img src='' alt='' /> */}
                <Icon name='profile' size={42} />
              </div>
              <div className={styles.userName}>예슬</div>
            </div>
          </div>
          <div className={styles.addUserBtnBox}>
            <button type='button' className={styles.addPerson}>
              <Icon name='add-person' size={50} />
            </button>
          </div>
        </div>
        <div className={styles.planner}>
          <div className={styles.planList}>
            <div className={styles.plan}>
              <div className={styles.dayTitle}>Day 1</div>
              <Element />
              <Element />
              <div className={styles.addElementBtn}>+</div>
            </div>
          </div>
          <div className={styles.addDayBtn}>추가하기</div>
        </div>
      </div>
      {chatModal ? (
        <Chatting
          userId={userId}
          chatList={chatList}
          newChat={newChat}
          onChatModalFalse={onChatModalFalse}
          onChatChange={onChatChange}
          onChatSubmit={onChatSubmit}
        />
      ) : (
        <button type='button' className={styles.chatModalBtn} onClick={onChatModalTrue}>
          <Icon name='chatting-dots' size={50} />
        </button>
      )}
    </div>
  )
}

function PlanDetail() {
  const plannerId = 39 // 임시 설정. useParams()로 받아오는 게 좋을 듯.
  const userId = 14 // 임시 설정. 로그인 기능 구현 후, 로그인한 유저의 id로 설정.
  const clientRef = useRef<StompJs.Client | null>(null)
  // 채팅 관련
  const [chatModal, setChatModal] = useState(false)
  const [chatList, setChatList] = useState<Chat[]>([])
  const [newChat, setNewChat] = useState('')

  useEffect(() => {
    // 1. 클라이언트 객체 생성
    const client = new StompJs.Client({
      brokerURL: import.meta.env.VITE_BROKER_URL,

      // 연결에 사용될 로그인 정보
      // connectHeaders: {
      //   Authorization: `Bearer ${token}`,
      // },

      // 디버깅용
      // debug(str) {
      //   console.log(str)
      // },

      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    })

    // 3. 클라이언트가 메시지 브로커에 연결(커넥트)되었을 때 호출되는 함수
    client.onConnect = function () {
      // 메시지 받는 함수
      const callback = function (message: any) {
        if (message.body) {
          const body = JSON.parse(message.body)
          if (body.type === 'chat') {
            setChatList(prev => [...prev, body.msg])
          }
          // todo: type에 따라 처리하면 될 것 같습니다.
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

  const onChatSubmit = (event: any) => {
    event.preventDefault()
    if (newChat.trim() !== '') {
      const newChatObj = { userId, message: newChat }
      const msg = JSON.stringify(newChatObj)
      // 4. 메시지 보내기(퍼블리시)
      if (clientRef.current) {
        clientRef.current.publish({ destination: `/pub/chat/${plannerId}`, body: msg })
      }
    }
    setNewChat('')
    event.target.reset()
  }

  const props: PlanDetailProps = {
    userId,
    chatModal,
    chatList,
    newChat,
    onChatModalTrue: () => setChatModal(true),
    onChatModalFalse: () => setChatModal(false),
    onChatChange: (event: any) => setNewChat(event.target.value),
    onChatSubmit,
  }

  return <PlanDetailView {...props} />
}

export default PlanDetail
