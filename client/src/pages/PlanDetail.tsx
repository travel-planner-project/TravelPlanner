import { useState, useEffect, useRef } from 'react'
import * as StompJs from '@stomp/stompjs'
import Icon from '../components/Common/Icon'
import Element from '../components/PlanDetail/PlanElement/Element'
import styles from './PlanDetail.module.scss'
import ChatModal from '../components/PlanDetail/ChatModal/ChatModal'
import {
  Chat,
  PlanDetailProps,
  ChattingProps,
  PlanDetailViewProps,
  ScheduleProps,
} from '../types/planDetailTypes'
import ElementEditor from '../components/PlanDetail/PlanElement/ElementEditor'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../store/store'
import { saveTokenToSessionStorage } from '../utils/tokenHandler'
import { getPlanDetail } from '../apis/planner'
import { refreshAccessToken } from '../apis/user'
import useRouter from '../hooks/useRouter'

// 높이 수정중

function PlanDetailView({
  userId,
  chatModal,
  chatList,
  newChat,
  onChatModalTrue,
  onChatModalFalse,
  onChatChange,
  onChatSubmit,
  planDetailData,
  scheduleData,
  handleChange,
  handleOptionChange,
  handleSubmit,
  handleOpenScheduleEditor,
  handleCloseScheduleEditor,
  currentDateId,
  isScheduleEditorOpened,
}: PlanDetailViewProps) {
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
              <Icon name='add-person' size={42} />
            </button>
          </div>
        </div>
        <div className={styles.planner}>
          <ul className={styles.planList}>
            {/* map으로 day 별로 묶인 큰 박스 맵핑 */}
            {planDetailData?.map((item, idx) => {
              return (
                <li className={styles.plan} key={item.dateId}>
                  {/* state 이용해서 input type date 혹은 날짜 보여주기*/}
                  {/* input type date는 idx가 0일 때만 */}
                  <div className={styles.dayTitle}>{item.dateTitle}</div>
                  <div className={styles.scheduleBox}>
                    {/* map으로 엘리먼트 맵핑. 넘겨주는 id에 day의 id 넣기? */}
                    <div className={styles.schedules}>
                      {item.scheduleItemList?.map((el, idx) => {
                        return (
                          <li className={styles.scheduleItem} key={el.itemId}>
                            <Element data={el} />
                          </li>
                        )
                      })}

                      {currentDateId === item.dateId && isScheduleEditorOpened ? (
                        <ElementEditor
                          scheduleData={scheduleData}
                          handleChange={handleChange}
                          handleOptionChange={handleOptionChange}
                          handleSubmit={handleSubmit}
                          dateId={item.dateId}
                        />
                      ) : (
                        <button
                          className={styles.addElementBtn}
                          onClick={() => handleOpenScheduleEditor(item.dateId)}
                        >
                          <Icon name='plus-square' size={24} />
                        </button>
                      )}
                    </div>
                  </div>
                </li>
              )
            })}
          </ul>
          <div className={styles.addDayBtn}>추가하기</div>
        </div>
      </div>
      {chatModal ? (
        <ChatModal
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
  const { params } = useRouter()
  const { planId } = params
  const { userId } = useRecoilValue(userInfo)
  const [token, setToken] = useState(sessionStorage.getItem('token'))
  const clientRef = useRef<StompJs.Client | null>(null)
  // 채팅 관련
  const [chatModal, setChatModal] = useState(false)
  const [chatList, setChatList] = useState<Chat[]>([])
  const [newChat, setNewChat] = useState('')
  // 플래너 관련
  const [currentDateId, setCurrentDateId] = useState(-1)
  const [isScheduleEditorOpened, setIsScheduleEditorOpened] = useState(false)
  const [planDetailData, setPlanDetailData] = useState<any>([])

  const handleOpenScheduleEditor = (id: number) => {
    setCurrentDateId(id)
    setIsScheduleEditorOpened(true)
  }

  const handleCloseScheduleEditor = () => {
    setCurrentDateId(-1)
    setIsScheduleEditorOpened(false)
  }

  const [scheduleData, setScheduleData] = useState({
    dateId: -1,
    itemTitle: '',
    category: '',
    itemDate: '',
    itemAddress: '',
    budget: 0,
    itemContent: '',
    isPrivate: false,
  })

  const handleChange = (field: string, value: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      [field]: value,
    }))
  }
  const handleOptionChange = (selectedOption: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      category: selectedOption,
    }))
  }

  const handleSubmit = (e: React.FormEvent, dateId: number) => {
    e.preventDefault()
    setScheduleData(prevData => ({
      ...prevData,
      dateId,
    }))
    console.log('Form data submitted:', scheduleData)

    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/create-todo/${planId}/${dateId}`,
        // 헤더에 엑세스 토큰 담기
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify(scheduleData),
      })
    }
    const resetData = {
      dateId: -1,
      itemTitle: '',
      category: '',
      itemDate: '',
      itemAddress: '',
      budget: 0,
      itemContent: '',
      isPrivate: false,
    }
    setScheduleData(resetData)
  }

  useEffect(() => {
    if (planId) {
      const fetchPlanDetailData = async () => {
        try {
          const res = await getPlanDetail(planId)
          if (res) setPlanDetailData(res.data.calendars)
        } catch (error) {
          console.error('Error fetching plan detail data:', error)
        }
      }
      fetchPlanDetailData()
    }
  }, [])

  useEffect(() => {
    // 1. 클라이언트 객체 생성
    const client = new StompJs.Client({
      brokerURL: import.meta.env.VITE_BROKER_URL,
      // 헤더에 엑세스 토큰 담기
      connectHeaders: {
        Authorization: `${token}`,
      },
      // debug(str) {
      //   console.log(str)
      // },
      reconnectDelay: 5000,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
    })

    // 3. 클라이언트가 메시지 브로커에 연결(커넥트)되었을 때 호출되는 함수
    client.onConnect = function () {
      // 메시지 받는 함수
      const callback = function (message: any) {
        if (message.body) {
          const resBody = JSON.parse(message.body)
          if (resBody.type === 'chat') {
            setChatList(prev => [...prev, resBody.msg])
          }
          if (resBody.type === 'add-schedule') {
            const newData = resBody.msg[resBody.msg.length - 1]
            const newDateId = newData.dateId
            const copyData = planDetailData
            const targetDateIndex = copyData.findIndex(el => el.dateId === newDateId)
            copyData[targetDateIndex].scheduleItemList.push(newData)
            setPlanDetailData(copyData)
          }
          // setChatList(prev => [...prev, JSON.parse(message.body)])
          // 추후 type으로 나눠서 처리
          // if (message.body.type === 'chat') {
          //   setChatList(prev => [...prev, JSON.parse(message.body)])
          // } else if (message.body.type === 'todo') {
          //   // 1. response의 dateId를 newDateId에 할당
          //   // 2. response를 newData에 할당
          //   // 3. 스케줄 리스트에서 newDateId와 일치하는 요소를 찾음
          //   // 4. 해당 요소에 newData 추가
          //   copyData.map((item) =>
          //   item.dateId === newDateId ? item.scheduleList.push(newData) : item
          //   )
          //   // 5. setData(copyData)
          // }
        }
      }
      // 구독하기
      client.subscribe(`/sub/planner-message/${planId}`, callback)
    }

    // 브로커에서 에러가 발생했을 때 호출되는 함수
    client.onStompError = function (frame) {
      // console.log(`Broker reported error: ${frame.headers.message}`)
      // console.log(`Additional details: ${frame.body}`)

      // 액세스 토큰 만료시
      if (
        frame.headers.message ===
        'Failed to send message to ExecutorSubscribableChannel[clientInboundChannel]'
      ) {
        refreshAccessToken().then(response => {
          if (response?.status === 200) {
            const { authorization } = response.headers
            saveTokenToSessionStorage(authorization)
            setToken(authorization) // 새로운 액세스 토큰으로 업데이트
          }
          if (response?.status !== 200) {
            console.error('Error refreshing access token:', response)
          }
        })
      }
    }

    // 2. 클라이언트 활성화 -> 설정된 주소로 연결을 시도하고, 연결이 성공하면 onConnect 콜백 함수 호출!
    client.activate()
    clientRef.current = client

    // 컴포넌트가 언마운트될 때 클라이언트 비활성화
    return () => {
      client.deactivate()
    }
  }, [token])

  const onChatSubmit = (event: any) => {
    event.preventDefault()
    if (newChat.trim() !== '') {
      const newChatObj = { userId, message: newChat }
      const msg = JSON.stringify(newChatObj)
      // 4. 메시지 보내기(퍼블리시)
      if (clientRef.current) {
        clientRef.current.publish({
          destination: `/pub/chat/${planId}`,
          // 헤더에 엑세스 토큰 담기
          headers: {
            Authorization: `${token}`,
          },
          body: msg,
        })
      }
    }
    setNewChat('')
    event.target.reset()
  }

  const onScheduleSubmit = () => {}

  const planDetailProps: PlanDetailProps = {
    userId,
    chatModal,
    onChatModalTrue: () => setChatModal(true),
  }

  const chattingProps: ChattingProps = {
    chatList,
    newChat,
    onChatModalFalse: () => setChatModal(false),
    onChatChange: (event: any) => setNewChat(event.target.value),
    onChatSubmit,
  }

  const scheduleProps: ScheduleProps = {
    planDetailData,
    currentDateId,
    isScheduleEditorOpened,
    handleOpenScheduleEditor,
    handleCloseScheduleEditor,
    handleChange,
    handleOptionChange,
    handleSubmit,
    scheduleData,
  }

  const props = { ...planDetailProps, ...chattingProps, ...scheduleProps }

  return <PlanDetailView {...props} />
}

export default PlanDetail
