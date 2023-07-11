import styles from './AddPlanModal.module.scss'

function AddPlanModal() {
  const today = new Date().toISOString().split('T')[0]

  const addPlanHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData(event.currentTarget)
    const planTitle = formData.get('title')
    const startDate = formData.get('start-date')
    const endDate = formData.get('end-date')
    console.log(planTitle, startDate, endDate)
  }

  return (
    <form className={styles.modalContainer} onSubmit={addPlanHandler}>
      <div className={styles.modalBackground} />
      <div className={styles.modal}>
        <div className={styles.title}>여행 추가하기</div>
        <div className={styles.inputBox}>
          <label className={styles.label}>
            여행 이름
            <input
              name='title'
              placeholder='여기에 입력하세요.'
              className={styles.tripTitle}
              type='text'
            />
          </label>
          <label className={styles.label}>
            여행 시작일
            <input
              name='start-date'
              defaultValue={today}
              className={styles.tripStartDate}
              type='date'
            />
          </label>
          <label className={styles.label}>
            여행 종료일
            <input
              name='end-date'
              defaultValue={today}
              className={styles.tripEndDate}
              type='date'
            />
          </label>
        </div>
        <div className={styles.buttons}>
          <button className={styles.okBtn} type='submit'>
            만들기
          </button>
          <button className={styles.cancelBtn} type='button'>
            취소
          </button>
        </div>
      </div>
    </form>
  )
}

export default AddPlanModal
