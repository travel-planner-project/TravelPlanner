import { useEffect, useState } from 'react'
import Icon from '../components/Common/Icon'
import styles from './Planner.module.scss'
import { createNewPlan, deletePlan, getCurrentUserPlanner } from '../apis/planner'
import PlanElement from '../components/Planner/PlanElement'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../store/store'
import Modal from '../components/Common/Modal/Modal'
import useModal from '../hooks/useModal'
import useRouter from '../hooks/useRouter'

type PlannerViewProps = {
  addPlan: () => void
  editPlan: () => void
  linkToDetail: (id: number) => void
  plannerList: PlannerDataType | null
  // isModalOpened: boolean
  modal: boolean
  // modalClose: () => void
  isEditing: boolean
  deletePlan: (id: number) => void
}

function PlannerView({
  addPlan,
  editPlan,
  deletePlan,
  linkToDetail,
  plannerList,
  modal,
  isEditing,
}: PlannerViewProps) {
  return (
    <>
      {modal ? <Modal type='create-planner' /> : null}
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
                <span>?</span>
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
                <div key={planner.plannerId}>
                  <PlanElement
                    planner={planner}
                    linkToDetail={() => linkToDetail(planner.plannerId)}
                  />
                  {isEditing ? (
                    <button type='button' onClick={() => deletePlan(+planner.plannerId)}>
                      delete
                    </button>
                  ) : null}
                </div>
              )
            })}
          </div>
          <div className={styles.buttons}>
            <button type='button' className={styles.addBtn} onClick={addPlan}>
              추가하기
            </button>
            <button type='button' className={styles.editBtn} onClick={editPlan}>
              편집하기
            </button>
          </div>
        </div>
      </div>
    </>
  )
}

type PlannerDataType = {
  // userId: number
  plannerId: number
  planTitle: string
  isPrivate: boolean
}[]

function Planner() {
  const { routeTo } = useRouter()

  const [plannerList, setPlannerList] = useState<PlannerDataType>([])

  const { modalData, openModal } = useModal()
  const { email } = useRecoilValue(userInfo)

  const [isEditingPlanner, setIsEditingPlanner] = useState<boolean>(false)

  const onClickAddNewPlan = async (planTitle: string) => {
    const newPlan = {
      planTitle,
      isPrivate: false,
    }
    const res = await createNewPlan(newPlan)
    if (res) setPlannerList(prev => [...prev, res.data])
  }

  const onClickDeletePlan = async (id: number) => {
    const res = await deletePlan(id)
    if (res) console.log(res)

    const fetchPlannerData = async () => {
      try {
        const res = await getCurrentUserPlanner(email)
        setPlannerList(res?.data.content)
      } catch (error) {
        console.error('Error fetching planner data:', error)
      }
    }

    fetchPlannerData()
  }

  const handleAddButtonClick = () => {
    openModal({
      title: '여행 추가하기',
      description: '여행의 이름을 입력하세요.',
      placeholder: '여행 이름',
      submitButton: '추가',
      onSubmit: onClickAddNewPlan,
    })
  }

  const handleEditButtonClick = () => {
    console.log('편집하기')
    setIsEditingPlanner(true)
  }
  const handlePlannerClick = (id: number) => {
    // 해당 element의 id 값을 가진 엔드포인트로 연결
    routeTo(`/plandetail/${id}`)
    console.log('플래너 상세 페이지')
  }

  useEffect(() => {
    const fetchPlannerData = async () => {
      try {
        const res = await getCurrentUserPlanner(email)
        setPlannerList(res?.data.content)
      } catch (error) {
        console.error('Error fetching planner data:', error)
      }
    }
    fetchPlannerData()
  }, [email])

  return (
    <PlannerView
      addPlan={handleAddButtonClick}
      editPlan={handleEditButtonClick}
      linkToDetail={handlePlannerClick}
      plannerList={plannerList}
      modal={modalData.isOpen}
      isEditing={isEditingPlanner}
      deletePlan={onClickDeletePlan}
    />
  )
}

export default Planner
