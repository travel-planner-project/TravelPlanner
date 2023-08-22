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
  PlanDetailDataType,
} from '../types/planDetailTypes'
import ElementEditor from '../components/PlanDetail/PlanElement/ElementEditor'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../store/store'
import { saveTokenToSessionStorage } from '../utils/tokenHandler'
import { getPlanDetail } from '../apis/planner'
import { refreshAccessToken } from '../apis/user'
import useRouter from '../hooks/useRouter'
import { dateFormat } from '../utils/date'

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
  onScheduleInputChange,
  onScheduleCategoryChange,
  onScheduleSubmit,
  handleOpenScheduleEditor,
  handleCloseScheduleEditor,
  handleAddDateBtnClick,
  isEditingDate,
  isEditingDateList,
  editingDateId,
  handleEditDateBtnClick,
  handleEditDateListBtnClick,
  handleEditDate,
  handleCancelEditingDate,
  handleDeleteDate,
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
                  {isEditingDate && item.dateId === editingDateId ? (
                    <input
                      type='date'
                      className={styles.dateInput}
                      required
                      aria-required='true'
                      data-placeholder='날짜 선택'
                      onChange={e => handleEditDate(item.dateId, e.target.value)}
                    />
                  ) : (
                    <>
                      <div className={styles.dateTitle}>{item.dateContent}</div>
                      {isEditingDateList ? (
                        <>
                          <button type='button' onClick={() => handleEditDateBtnClick(item.dateId)}>
                            날짜 수정하기
                          </button>
                          <button type='button' onClick={() => handleDeleteDate(item.dateId)}>
                            날짜 삭제하기
                          </button>
                        </>
                      ) : null}
                    </>
                  )}
                  <div className={styles.scheduleBox}>
                    {/* map으로 엘리먼트 맵핑. 넘겨주는 id에 day의 id 넣기? */}
                    <ul className={styles.schedules}>
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
                          handleChange={onScheduleInputChange}
                          handleOptionChange={onScheduleCategoryChange}
                          handleSubmit={onScheduleSubmit}
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
                    </ul>
                  </div>
                </li>
              )
            })}
          </ul>
          <button type='button' className={styles.addDayBtn} onClick={handleAddDateBtnClick}>
            여행 일정 추가
          </button>
          {!isEditingDateList ? (
            <button type='button' className={styles.addDayBtn} onClick={handleEditDateListBtnClick}>
              편집
            </button>
          ) : (
            <button type='button' className={styles.addDayBtn} onClick={handleCancelEditingDate}>
              편집 그만하기
            </button>
          )}
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
  const initialScheduleData = {
    // dateId: -1,
    itemTitle: '',
    category: '',
    itemDate: '',
    itemAddress: '',
    budget: 0,
    itemContent: '',
    isPrivate: false,
  }

  const { params } = useRouter()
  const { plannerId } = params
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
  const [planDetailData, setPlanDetailData] = useState<PlanDetailDataType>([])
  console.log('외부: ', planDetailData)

  // 스케줄 수정 관련
  const [isEditingDateList, setIsEditingDateList] = useState<boolean>(false)

  // date 수정 관련
  const [isEditingDate, setIsEditingDate] = useState<boolean>(false)
  const [editingDateId, setEditingDateId] = useState<number>(-1)

  // 단일 스케줄 관련
  const [scheduleData, setScheduleData] = useState(initialScheduleData)

  const handleAddDateBtnClick = () => {
    // console.log('add-date')

    if (clientRef.current) {
      let requestBody = {}
      const currentDate = new Date()

      if (planDetailData.length === 0) {
        requestBody = { dateTitle: currentDate }
      } else {
        if (planDetailData[planDetailData.length - 1].dateTitle === 'none') {
          alert('먼저 여행 시작일을 입력하세요.')
        }
        const lastDate = new Date(planDetailData[planDetailData.length - 1].dateTitle)
        lastDate.setDate(lastDate.getDate() + 1)
        requestBody = { dateTitle: lastDate.toISOString() }
      }

      clientRef.current.publish({
        destination: `/pub/create-date/${plannerId}`,
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify(requestBody),
      })
    }
  }

  const handleEditDateListBtnClick = () => {
    setIsEditingDateList(true)
  }

  const handleEditDateBtnClick = (id: number) => {
    setEditingDateId(id)
    setIsEditingDate(true)
  }

  const handleEditDate = (id: number, date: string) => {
    const convertedDate = new Date(date).toISOString()
    const modifiedData = planDetailData.map(el => {
      if (el.dateId === id) {
        return { ...el, dateContent: dateFormat(new Date(convertedDate)) }
      }
      return { ...el } // 깊은 복사를 위해 새로운 객체로 복사
    })
    setPlanDetailData(modifiedData)

    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/update-date/${plannerId}/${id}`,
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify({ dateId: id, dateTitle: convertedDate }),
      })
    }
    setEditingDateId(-1)
  }

  const handleCancelEditingDate = () => {
    setIsEditingDateList(false)
    setIsEditingDate(false)
  }

  const handleDeleteDate = (id: number) => {
    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/delete-date/${plannerId}/${id}`,
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify({ dateId: id }),
      })
    }
    const modifiedData = planDetailData.filter(el => el.dateId !== id)
    setPlanDetailData(modifiedData)
    setEditingDateId(-1)
  }

  const handleOpenScheduleEditor = (id: number) => {
    setCurrentDateId(id)
    setIsScheduleEditorOpened(true)
  }

  const handleCloseScheduleEditor = () => {
    setCurrentDateId(-1)
    setIsScheduleEditorOpened(false)
  }

  const onScheduleInputChange = (field: string, value: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      [field]: value,
    }))
  }
  const onScheduleCategoryChange = (selectedOption: string) => {
    setScheduleData(prevData => ({
      ...prevData,
      category: selectedOption,
    }))
  }

  const onScheduleSubmit = (e: React.FormEvent, dateId: number) => {
    e.preventDefault()
    setScheduleData(prevData => ({
      ...prevData,
      // dateId,
    }))

    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/create-todo/${plannerId}/${dateId}`,
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify(scheduleData),
      })
    }
    setScheduleData(initialScheduleData)
  }

  const onChatSubmit = (event: any) => {
    event.preventDefault()
    if (newChat.trim() !== '') {
      const newChatObj = { userId, message: newChat }
      const msg = JSON.stringify(newChatObj)
      // 4. 메시지 보내기(퍼블리시)
      if (clientRef.current) {
        clientRef.current.publish({
          destination: `/pub/chat/${plannerId}`,
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

  useEffect(() => {
    if (plannerId) {
      const fetchPlanDetailData = async () => {
        try {
          const res = await getPlanDetail(plannerId)
          if (res) {
            // 스케줄 state 세팅
            const schedules = res.data.calendars.map(el => ({
              ...el, // Keep the other properties unchanged
              dateContent: dateFormat(new Date(el.dateTitle)),
            }))
            setPlanDetailData(schedules)
          }
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
          // console.log('웹소켓 리스폰스: ', resBody)
          if (resBody.type === 'chat') {
            setChatList(prev => [...prev, resBody.msg])
          } else if (resBody.type === 'add-date') {
            const newDate = resBody.msg
            newDate.dateContent = dateFormat(new Date(newDate.dateTitle))
            setPlanDetailData(prev => [...prev, newDate])
          } else if (resBody.type === 'modify-date') {
            setIsEditingDate(false)
            setIsEditingDateList(false)
          } else if (resBody.type === 'add-schedule') {
            const newData = resBody.msg[resBody.msg.length - 1]
            const newDateId = newData.dateId
            const copyData = planDetailData
            const targetDateIndex = copyData.findIndex(
              (el: { dateId: number }) => el.dateId === newDateId
            )
            copyData[targetDateIndex].scheduleItemList.push(newData)
            setPlanDetailData(copyData)
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
    handleAddDateBtnClick,
    isEditingDate,
    isEditingDateList,
    editingDateId,
    handleEditDateBtnClick,
    handleEditDateListBtnClick,
    handleEditDate,
    handleCancelEditingDate,
    handleDeleteDate,
    handleOpenScheduleEditor,
    handleCloseScheduleEditor,
    onScheduleInputChange,
    onScheduleCategoryChange,
    onScheduleSubmit,
    scheduleData,
  }

  const props = { ...planDetailProps, ...chattingProps, ...scheduleProps }

  return <PlanDetailView {...props} />
}

export default PlanDetail
