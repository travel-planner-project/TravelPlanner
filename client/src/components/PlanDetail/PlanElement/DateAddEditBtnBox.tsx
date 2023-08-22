import styles from './DateAddEditBtnBox.module.scss'

type DateAddEditBtnBoxProps = {
  handleAdd: () => void
  handleEdit: () => void
  handleCancelEditing: () => void
  isEditingDateList: boolean
}

function DateAddEditBtnBox({
  handleAdd,
  handleEdit,
  handleCancelEditing,
  isEditingDateList,
}: DateAddEditBtnBoxProps) {
  return (
    <div className={styles.dateAddEditBtnBox}>
      <button type='button' className={styles.addDayBtn} onClick={handleAdd}>
        일정 추가
      </button>
      {!isEditingDateList ? (
        <button type='button' className={styles.addDayBtn} onClick={handleEdit}>
          일정 편집
        </button>
      ) : (
        <button type='button' className={styles.addDayBtn} onClick={handleCancelEditing}>
          편집 종료
        </button>
      )}
    </div>
  )
}

export default DateAddEditBtnBox
