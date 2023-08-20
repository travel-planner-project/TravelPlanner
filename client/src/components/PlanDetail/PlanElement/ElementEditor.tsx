import { useRef, useState } from 'react'
import styles from './ElementEditor.module.scss'
import DropDown from './DropDown'

type dataType = {
  // dateId: number
  itemTitle: string
  itemDate: string
  category: string | null
  budget: number
  itemContent: string
  isPrivate: boolean
  itemAddress: string
}

interface ElementEditorViewProps extends React.HTMLProps<HTMLTextAreaElement> {
  textarea: React.RefObject<HTMLTextAreaElement>
  handleResizeHeight: () => void
  handleChange: (field: string, value: string) => void
  handleSubmit: (e: React.FormEvent, dateId: number) => void
  handleOptionChange: (selectedOption: string) => void
  scheduleData: dataType
  dateId: number
}

const options = [
  { title: '관광', key: 1 },
  { title: '숙박', key: 2 },
  { title: '교통', key: 3 },
  { title: '카페', key: 4 },
  { title: '식당', key: 5 },
  { title: '기타', key: 6 },
]

function ElementEditorView({
  textarea,
  handleResizeHeight,
  handleChange,
  handleSubmit,
  scheduleData,
  handleOptionChange,
  dateId,
}: ElementEditorViewProps) {
  return (
    <form className={styles.container} onSubmit={e => handleSubmit(e, dateId)}>
      <div className={styles.inputBox}>
        <input
          className={styles.textInput}
          type='text'
          placeholder='일정 제목'
          value={scheduleData.itemTitle}
          onChange={e => handleChange('itemTitle', e.target.value)}
        />
        <DropDown options={options} onOptionChange={handleOptionChange} />
        <input
          className={styles.timeInput}
          type='time'
          placeholder='시간'
          value={scheduleData.itemDate}
          onChange={e => handleChange('itemDate', e.target.value)}
        />
        <input
          className={styles.textInput}
          type='text'
          placeholder='주소'
          value={scheduleData.itemAddress}
          onChange={e => handleChange('itemAddress', e.target.value)}
        />
        <input
          className={styles.textInput}
          type='text'
          placeholder='지출 금액 (선택 사항)'
          value={scheduleData.budget}
          onChange={e => handleChange('budget', e.target.value)}
        />
        <textarea
          className={styles.longTextInput}
          rows={1}
          placeholder='세부 내용 (선택 사항)'
          ref={textarea}
          value={scheduleData.itemContent}
          onChange={e => {
            handleChange('itemContent', e.target.value)
            handleResizeHeight()
          }}
        />
      </div>
      <div className={styles.buttons}>
        <button type='submit' className={styles.addBtn}>
          추가
        </button>
        <div className={styles.cancelBtn}>취소</div>
      </div>
    </form>
  )
}

type ElementEditorProps = {
  handleChange: (field: string, value: string) => void
  handleSubmit: (e: React.FormEvent, dateId: number) => void
  handleOptionChange: (selectedOption: string) => void
  scheduleData: dataType
  dateId: number
}

function ElementEditor({
  handleChange,
  handleSubmit,
  handleOptionChange,
  scheduleData,
  dateId,
}: ElementEditorProps) {
  const textarea = useRef<HTMLTextAreaElement>(null)
  const handleResizeHeight = () => {
    if (textarea.current) {
      textarea.current.style.height = 'auto'
      textarea.current.style.height = `${textarea.current.scrollHeight}px`
    }
  }

  const props = {
    textarea,
    handleResizeHeight,
    scheduleData,
    handleChange,
    handleSubmit,
    handleOptionChange,
    dateId,
  }
  return <ElementEditorView {...props} />
}

export default ElementEditor
