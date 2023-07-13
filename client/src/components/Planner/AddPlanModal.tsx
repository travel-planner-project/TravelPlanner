import styles from './AddPlanModal.module.scss'

type AddPlanModalProps = {
  modalClose: () => void
}

function AddPlanModal({ modalClose }: AddPlanModalProps) {
  const today = new Date().toISOString().split('T')[0]

  const addPlanHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const formData = new FormData(event.currentTarget)
    const planTitle = formData.get('title')
    const startDate = new Date(formData.get('start-date') as string)
    const endDate = new Date(formData.get('end-date') as string)
    console.log(planTitle, startDate, endDate)
  }

  return (
    <form className={styles.modalContainer} onSubmit={addPlanHandler}>
      <div className={styles.modalBackground} />
      <div className={styles.modal}>
        <div className={styles.title}>여행 추가하기</div>
        <div className={styles.inputBox}>
          <label htmlFor='title' className={styles.label}>
            여행 이름
            <input
              id='title'
              name='title'
              placeholder='여기에 입력하세요.'
              className={styles.tripTitle}
              type='text'
            />
          </label>
          <label htmlFor='start-date' className={styles.label}>
            여행 시작일
            <input
              id='start-date'
              name='start-date'
              defaultValue={today}
              className={styles.tripStartDate}
              type='date'
            />
          </label>
          <label htmlFor='end-date' className={styles.label}>
            여행 종료일
            <input
              id='end-date'
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
          <button className={styles.cancelBtn} type='button' onClick={modalClose}>
            취소
          </button>
        </div>
      </div>
    </form>
  )
}

export default AddPlanModal
