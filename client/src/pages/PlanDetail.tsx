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
  DateListType,
  ScheduleType,
  DateType,
} from '../types/planDetailTypes'
import ElementEditor from '../components/PlanDetail/PlanElement/ElementEditor'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../store/store'
import { saveTokenToSessionStorage } from '../utils/tokenHandler'
import { getPlanDetail } from '../apis/planner'
import { refreshAccessToken } from '../apis/user'
import useRouter from '../hooks/useRouter'
import { dateFormat, dateFormatDash } from '../utils/date'
import DateInputBox from '../components/PlanDetail/PlanElement/DateInputBox'
import EditingDateButtonBox from '../components/PlanDetail/PlanElement/EditingDateButtonBox'
import DateAddEditBtnBox from '../components/PlanDetail/PlanElement/DateAddEditBtnBox'
import { initialScheduleData, usePlanDetailManagement } from '../utils/usePlanDetailManagement'

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
  dateListData,
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
  handleCancelEditingDateList,
  handleDeleteDate,
  currentDateId,
  isScheduleEditorOpened,
  handleDeleteSchedule,
  handleEditScheduleBtnClick,
  handleEditSchedule,
  editingScheduleId,
  handleCancelEditingScheduleBtnClick,
}: PlanDetailViewProps) {
  return (
    <div className={styles.planContainer}>
      <div className={styles.planHeader}>
        <div className={styles.planTitle}>제주 여행</div>
        <div className={styles.planPeriodBox}>
          <div className={styles.planPeriod}>
            <Icon name='calendar' size={16} />
            <div className={styles.dateBox}>
              <div className={styles.startDate}>
                {dateListData?.length >= 1
                  ? dateFormatDash(new Date(dateListData[0].dateTitle))
                  : dateFormatDash(new Date())}
              </div>
              <span> ~ </span>
              <div className={styles.endDate}>
                {dateListData?.length >= 2
                  ? dateFormatDash(new Date(dateListData[dateListData.length - 1].dateTitle))
                  : dateFormatDash(new Date())}
              </div>
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
            {dateListData?.map(item => {
              return (
                <li className={styles.plan} key={item.dateId}>
                  {isEditingDate && item.dateId === editingDateId ? (
                    <DateInputBox
                      item={item}
                      handleEdit={handleEditDate}
                      handleCancel={handleCancelEditingDate}
                    />
                  ) : (
                    <>
                      <div className={styles.dateTitle}>{dateFormat(new Date(item.dateTitle))}</div>
                      {isEditingDateList ? (
                        <EditingDateButtonBox
                          handleEdit={handleEditDateBtnClick}
                          handleDelete={handleDeleteDate}
                          dateId={item.dateId}
                        />
                      ) : null}
                    </>
                  )}
                  <div className={styles.scheduleBox}>
                    {/* map으로 엘리먼트 맵핑. 넘겨주는 id에 day의 id 넣기? */}
                    <ul className={styles.schedules}>
                      {item.scheduleItemList?.map(el => {
                        return (
                          <li className={styles.scheduleItem} key={el.itemId}>
                            {editingScheduleId === el.itemId ? (
                              <ElementEditor
                                scheduleData={scheduleData}
                                handleChange={onScheduleInputChange}
                                handleOptionChange={onScheduleCategoryChange}
                                handleEdit={handleEditSchedule}
                                handleCancel={handleCancelEditingScheduleBtnClick}
                                dateId={item.dateId}
                                type='edit'
                              />
                            ) : (
                              <Element
                                data={el}
                                handleDelete={handleDeleteSchedule}
                                handleEditBtnClick={handleEditScheduleBtnClick}
                                dateId={item.dateId}
                              />
                            )}
                          </li>
                        )
                      })}

                      {currentDateId === item.dateId &&
                      isScheduleEditorOpened &&
                      editingScheduleId === -1 ? (
                        <ElementEditor
                          scheduleData={scheduleData}
                          handleChange={onScheduleInputChange}
                          handleOptionChange={onScheduleCategoryChange}
                          handleSubmit={onScheduleSubmit}
                          handleCancel={handleCloseScheduleEditor}
                          dateId={item.dateId}
                          type='add'
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
          <DateAddEditBtnBox
            handleAdd={handleAddDateBtnClick}
            handleEdit={handleEditDateListBtnClick}
            handleCancelEditing={handleCancelEditingDateList}
            isEditingDateList={isEditingDateList}
          />
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
  const clientRef = useRef<StompJs.Client | null>(null)

  const {
    // 스케줄 에디터 관련
    isScheduleEditorOpened,
    setIsScheduleEditorOpened,
    // 플래너 수정 관련
    editingScheduleId,
    setEditingScheduleId,
    // 날짜 수정 관련
    currentDateId,
    setCurrentDateId,
    isEditingDateList,
    setIsEditingDateList,
    isEditingDate,
    setIsEditingDate,
    editingDateId,
    setEditingDateId,
    handleEditDateListBtnClick,
    handleEditDateBtnClick,
    handleCancelEditingDate,
    handleCancelEditingDateList,
    // 스케줄 수정 관련
    handleOpenScheduleEditor,
    handleCloseScheduleEditor,
    handleCancelEditingScheduleBtnClick,
    onScheduleCategoryChange,
    onScheduleInputChange,
    // 단일 스케줄 최신화 관련
    scheduleData,
    setScheduleData,
  } = usePlanDetailManagement()

  const { params } = useRouter()
  const { plannerId } = params
  const { userId } = useRecoilValue(userInfo)

  const [token, setToken] = useState(sessionStorage.getItem('token'))

  // 채팅 관련
  const [chatModal, setChatModal] = useState(false)
  const [chatList, setChatList] = useState<Chat[]>([])
  const [newChat, setNewChat] = useState('')

  // 플래너 관련
  const [dateListData, setDateListData] = useState<DateListType>([])

  const handleAddDateBtnClick = () => {
    if (clientRef.current) {
      let requestBody = {}
      const currentDate = new Date()

      if (dateListData.length === 0) {
        requestBody = { dateTitle: currentDate }
      } else {
        if (dateListData[dateListData.length - 1].dateTitle === 'none') {
          alert('먼저 여행 시작일을 입력하세요.')
        }
        const lastDate = new Date(dateListData[dateListData.length - 1].dateTitle)
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

  const handleEditDate = (id: number, date: string) => {
    const convertedDate = new Date(date).toISOString()
    const modifiedData = dateListData.map(el => {
      if (el.dateId === id) {
        return { ...el, dateTitle: date }
      }
      return { ...el } // 깊은 복사를 위해 새로운 객체로 복사
    })
    setDateListData(modifiedData)

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

  const handleDeleteDate = (id: number) => {
    if (window.confirm('OK 버튼을 누르면 해당 날짜의 모든 스케줄이 지워집니다.')) {
      if (clientRef.current) {
        clientRef.current.publish({
          destination: `/pub/delete-date/${plannerId}/${id}`,
          headers: {
            Authorization: `${token}`,
          },
          body: JSON.stringify({ dateId: id }),
        })
      }
    } else return
  }

  const onScheduleSubmit = (e: React.FormEvent, dateId: number) => {
    e.preventDefault()
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

  const handleDeleteSchedule = (dateId: number, itemId: number) => {
    if (isEditingDate || isEditingDateList) {
      alert('일정 편집을 종료한 후에 다시 시도해주세요.')
      return
    }
    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/delete-todo/${plannerId}/${dateId}/${itemId}`,
        headers: {
          Authorization: `${token}`,
        },
      })
    }
  }

  const handleEditScheduleBtnClick = (dateId: number, itemId: number) => {
    if (isEditingDate || isEditingDateList) {
      alert('일정 편집을 종료한 후에 다시 시도해주세요.')
      return
    }
    const targetDateIndex = dateListData.findIndex(el => el.dateId === dateId)
    const currentList = dateListData[targetDateIndex].scheduleItemList
    const targetScheduleIndex = currentList.findIndex(el => el.itemId === itemId)
    setScheduleData(currentList[targetScheduleIndex])
    setEditingScheduleId(itemId)
  }

  const handleEditSchedule = (e: React.FormEvent, dateId: number, itemId: number) => {
    e.preventDefault()
    if (clientRef.current) {
      clientRef.current.publish({
        destination: `/pub/update-todo/${plannerId}/${dateId}/${itemId}`,
        headers: {
          Authorization: `${token}`,
        },
        body: JSON.stringify(scheduleData),
      })
    }
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
            const schedules = res.data.calendars
            setDateListData(schedules)
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
          // 아예 message.body를 받지 못하는 경우의 에러 처리는 어떻게 할 것인가?
          const resBody = JSON.parse(message.body)
          if (resBody.type === 'chat') {
            setChatList(prev => [...prev, resBody.msg])
          } else if (resBody.type === 'add-date') {
            const newDate = resBody.msg
            if (newDate) {
              setDateListData(prev => [...prev, newDate])
            } else {
              alert('문제가 발생했습니다. 페이지를 새로고침해주세요!')
            }
          } else if (resBody.type === 'modify-date') {
            const data = resBody.msg
            const targetDateIndex = dateListData.findIndex(
              (date: DateType) => date.dateId === data.dateId
            )
            const targetScheduleIndex = dateListData[targetDateIndex].scheduleItemList.findIndex(
              (schedule: ScheduleType) => schedule.itemId === data.itemId
            )
            dateListData[targetDateIndex].scheduleItemList[targetScheduleIndex] = scheduleData
            const modifiedData = JSON.parse(JSON.stringify(dateListData))
            setDateListData(modifiedData)
            setIsEditingDate(false)
            setIsEditingDateList(false)
          } else if (resBody.type === 'delete-date') {
            const schedules = resBody.msg
            setDateListData(schedules)
          } else if (resBody.type === 'add-schedule') {
            const newData = resBody.msg
            const newDateId = newData.dateId
            // 배열 내 특정 날짜 객체 찾기
            const updatedDateList = dateListData.map((date: DateType) => {
              if (date.dateId === newDateId) {
                return {
                  ...date,
                  scheduleItemList: [...date.scheduleItemList, newData], // 스케줄 추가
                }
              }
              return { ...date }
            })
            if (updatedDateList.length === 0) {
              alert('문제가 발생했습니다. 페이지를 새로고침해주세요!')
            } else {
              setDateListData(updatedDateList)
            }
          } else if (resBody.type === 'delete-schedule') {
            const schedules = resBody.msg
            setDateListData(schedules)
          } else if (resBody.type === 'modify-schedule') {
            setEditingScheduleId(-1)
            setScheduleData(initialScheduleData)
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
    dateListData,
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
    handleCancelEditingDateList,
    handleDeleteDate,
    handleOpenScheduleEditor,
    handleCloseScheduleEditor,
    onScheduleInputChange,
    onScheduleCategoryChange,
    onScheduleSubmit,
    scheduleData,
    handleDeleteSchedule,
    handleEditScheduleBtnClick,
    handleEditSchedule,
    editingScheduleId,
    handleCancelEditingScheduleBtnClick,
  }

  const props = { ...planDetailProps, ...chattingProps, ...scheduleProps }

  return <PlanDetailView {...props} />
}

export default PlanDetail
