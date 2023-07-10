import { useEffect, useState } from 'react'
import Icon from '../components/Common/Icon'
import styles from './Planner.module.scss'
import getCurrentUserPlanner from '../apis/planner'
import PlanElement from '../components/Planner/PlanElement'

type PlannerViewProps = {
  add: () => void
  edit: () => void
  linkToDetail: () => void
  plannerList: PlannerDataType | null
}

function PlannerView({ add, edit, linkToDetail, plannerList }: PlannerViewProps) {
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
              <span>{plannerList?.length}</span>
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
          {plannerList?.map(planner => {
            return (
              <PlanElement key={planner.plannerId} planner={planner} linkToDetail={linkToDetail} />
            )
          })}
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

type PlannerDataType = {
  userId: number
  plannerId: number
  planTitle: string
}[]

function Planner() {
  const [plannerList, setPlannerList] = useState<PlannerDataType | null>(null)
  const apiUrl = `${import.meta.env.VITE_API_SERVER}/planner?userId=14`

  const handleAddButtonClick = () => {
    console.log('추가하기')
  }
  const handleEditButtonClick = () => {
    console.log('편집하기')
  }
  const handlePlannerClick = () => {
    console.log('플래너 상세 페이지')
  }

  useEffect(() => {
    const fetchPlannerData = async () => {
      try {
        const data = await getCurrentUserPlanner(apiUrl)
        setPlannerList(data.content)
      } catch (error) {
        console.error('Error fetching planner data:', error)
      }
    }
    fetchPlannerData()
  }, [apiUrl])

  return (
    <PlannerView
      add={handleAddButtonClick}
      edit={handleEditButtonClick}
      linkToDetail={handlePlannerClick}
      plannerList={plannerList}
    />
  )
}

export default Planner
