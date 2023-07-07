import styles from './PlanDetail.module.scss'

function PlanDetailView() {
  return (
    <div className={styles.planContainer}>
      <div className={styles.planHeader}>1</div>
      <div className={styles.planBody}>
        <div className={styles.planPeriodBox}>
          <div className={styles.planPeriod}>
            <div className={styles.startDate}>2023-07-14</div>
            <span> ~ </span>
            <div className={styles.endDate}>2023-07-16</div>
          </div>
        </div>
        <div className={styles.userList}>
          <div className={styles.users}>
            <div className={styles.user}>
              <div className={styles.userProfileBox}>
                <img src='' alt='' />
              </div>
              <div className={styles.userName}>시은</div>
            </div>
          </div>
          <div className={styles.addUserBtnBox}>
            <button type='button'>addUser</button>
          </div>
        </div>
        <div className={styles.planner}>
          <div className={styles.planList}>
            <div className={styles.plan}>
              <div className={styles.planTitle}>Day 1</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
function PlanDetail() {
  return <PlanDetailView />
}

export default PlanDetail
