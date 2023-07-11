import { useState } from 'react'
import Icon from '../components/Common/Icon'
import Element from '../components/PlanDetail/PlanElement/Element'
import styles from './PlanDetail.module.scss'
import Chatting from '../components/Planner/Chatting'

function PlanDetailView() {
  const [chatModal, setChatModal] = useState(false)

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
        <Chatting setChatModal={setChatModal} />
      ) : (
        <button
          type='button'
          className={styles.chatModalBtn}
          onClick={(event: React.MouseEvent<HTMLButtonElement>) => setChatModal(true)}
        >
          <Icon name='chatting-dots' size={50} />
        </button>
      )}
    </div>
  )
}
function PlanDetail() {
  return <PlanDetailView />
}

export default PlanDetail
