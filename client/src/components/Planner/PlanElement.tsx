import styles from './PlanElement.module.scss'

type PlanElementProps = {
  planner: { plannerId: number; planTitle: string; isPrivate: boolean }
  linkToDetail: () => void
}

function PlanElement({ planner, linkToDetail }: PlanElementProps) {
  return (
    <div className={styles.planBox}>
      <div className={styles.planHeader}>
        <button type='button' onClick={linkToDetail}>
          {planner.planTitle}
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
        {/* <div className={styles.tripPeriod}>
          <div className={styles.startDate}>2023. 07. 14</div>
          <span>~</span>
          <div className={styles.endDate}>2023. 07. 17</div>
        </div> */}
      </div>
    </div>
  )
}

export default PlanElement
