import { useState } from 'react'
import styles from './DateInputBox.module.scss'
import { DateType } from '../../../types/planDetailTypes'

type DateInputBoxProps = {
  handleEdit: (id: number, date: string) => void
  handleCancel: () => void
  item: DateType
}

function DateInputBox({ handleEdit, handleCancel, item }: DateInputBoxProps) {
  const [currentDate, setCurrentDate] = useState('')
  const handleChangeDate = (date: string) => {
    setCurrentDate(date)
  }
  return (
    <form
      onSubmit={() => handleEdit(item.dateId, currentDate)}
      className={styles.dateInputBoxContainer}
    >
      <input
        type='date'
        className={styles.dateInput}
        required
        aria-required='true'
        data-placeholder='날짜 선택'
        onChange={e => handleChangeDate(e.target.value)}
      />
      <div className={styles.buttonBox}>
        <button type='submit'>변경</button>
        <button type='button' onClick={handleCancel}>
          취소
        </button>
      </div>
    </form>
  )
}

export default DateInputBox
