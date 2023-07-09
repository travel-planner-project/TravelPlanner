import Icon from '../components/Common/Icon'
import styles from './Planner.module.scss'

type PlannerViewProps = {
  add: () => void
  edit: () => void
  openDetail: () => void
}

function PlannerView({ add, edit, openDetail }: PlannerViewProps) {
  return (
    <div className={styles.plannerContainer}>
      <div className={styles.header}>
        <div className={styles.profileBox}>
          <img className={styles.profileImage} src='' alt='' />
          <Icon name='profile' size={64} />
        </div>
        <div className={styles.describtionBox}>
          <div className={styles.tripDescribtion}>
            <div className={styles.pastTripCount}>
              <span>지난 여행 </span>
              <span>1</span>
              <span>개</span>
            </div>
            <span className={styles.grayText}>|</span>
            <div className={styles.entireTripCount}>
              <span>총 여행 계획 </span>
              <span>2</span>
              <span>개</span>
            </div>
          </div>
          <div className={styles.descriptionMsg}>
            <span>다가오는 </span>
            <span>1</span>
            <span>개의 여행이 있어요.</span>
          </div>
        </div>
      </div>
      <div className={styles.plansBox}>
        <div className={styles.plans}>
          {/* 플래너 리스트 맵으로 돌리기 */}
          <div className={styles.planBox}>
            <div className={styles.planHeader}>
              <button type='button' onClick={openDetail}>
                제주 여행
              </button>
            </div>
            <div className={styles.planContents}>
              <div className={styles.userList}>
                {/* 유저 리스트 맵으로 돌리기 */}
                <div className={styles.plannerUser}>
                  <div className={styles.plannerUserProfileBox}>
                    {/* <img className={styles.plannerUserProfile} src='' alt='' /> */}
                    {/* <Icon name='profile' size={28} /> */}
                  </div>
                  <div className={styles.plannerUserName}>유저 1</div>
                </div>
                <div className={styles.plannerUser}>
                  <div className={styles.plannerUserProfileBox}>
                    {/* <img className={styles.plannerUserProfile} src='' alt='' /> */}
                  </div>
                  <div className={styles.plannerUserName}>유저 2</div>
                </div>
              </div>
              <div className={styles.tripPeriod}>
                <div className={styles.startDate}>2023. 07. 14</div>
                <span>~</span>
                <div className={styles.endDate}>2023. 07. 17</div>
              </div>
            </div>
          </div>
        </div>
        <div className={styles.buttons}>
          <button type='button' className={styles.addBtn} onClick={add}>
            추가하기
          </button>
          <button type='button' className={styles.editBtn} onClick={edit}>
            편집하기
          </button>
        </div>
      </div>
    </div>
  )
}

function Planner() {
  const handleAddButtonClick = () => {
    console.log('추가하기')
  }
  const handleEditButtonClick = () => {
    console.log('편집하기')
  }
  const handlePlannerClick = () => {
    console.log('플래너 상세 페이지')
  }
  return (
    <PlannerView
      add={handleAddButtonClick}
      edit={handleEditButtonClick}
      openDetail={handlePlannerClick}
    />
  )
}

export default Planner