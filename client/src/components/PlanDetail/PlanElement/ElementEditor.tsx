import { useRef } from 'react'
import styles from './ElementEditor.module.scss'
import DropDown from './DropDown'
import { ScheduleType } from '../../../types/planDetailTypes'

interface ElementEditorViewProps extends React.HTMLProps<HTMLTextAreaElement> {
  textarea: React.RefObject<HTMLTextAreaElement>
  handleResizeHeight: () => void
  handleChange: (field: string, value: string) => void
  handleSubmit?: (e: React.FormEvent, dateId: number) => void
  handleEdit?: (e: React.FormEvent, dateId: number, itemId: number) => void
  handleOptionChange: (selectedOption: string) => void
  handleCancel: () => void
  scheduleData: ScheduleType
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
  handleCancel,
  handleEdit,
  dateId,
  type,
}: ElementEditorViewProps) {
  return (
    <form
      className={styles.container}
      onSubmit={
        type === 'add'
          ? e => handleSubmit!(e, dateId)
          : e => handleEdit!(e, dateId, scheduleData.itemId)
      }
    >
      <div className={styles.inputBox}>
        <input
          className={styles.textInput}
          type='text'
          placeholder='일정 제목'
          value={scheduleData.itemTitle || ''}
          onChange={e => handleChange('itemTitle', e.target.value)}
        />
        <DropDown
          options={options}
          onOptionChange={handleOptionChange}
          current={scheduleData.category}
        />
        <input
          className={styles.timeInput}
          type='time'
          placeholder='시간'
          value={scheduleData.itemTime || ''}
          onChange={e => handleChange('itemTime', e.target.value)}
        />
        <input
          className={styles.textInput}
          type='text'
          placeholder='주소'
          value={scheduleData.itemAddress || ''}
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
          value={scheduleData.itemContent || ''}
          onChange={e => {
            handleChange('itemContent', e.target.value)
            handleResizeHeight()
          }}
        />
      </div>
      <div className={styles.buttons}>
        {type === 'add' ? (
          <button type='submit' className={styles.addBtn}>
            추가
          </button>
        ) : (
          <button type='submit' className={styles.addBtn}>
            변경
          </button>
        )}
        <button type='button' className={styles.cancelBtn} onClick={handleCancel}>
          취소
        </button>
      </div>
    </form>
  )
}

type ElementEditorProps = {
  handleChange: (field: string, value: string) => void
  handleSubmit?: (e: React.FormEvent, dateId: number) => void
  handleEdit?: (e: React.FormEvent, dateId: number, itemId: number) => void
  handleOptionChange: (selectedOption: string) => void
  handleCancel: () => void
  scheduleData: ScheduleType
  dateId: number
  type: string
}

function ElementEditor({
  handleChange,
  handleSubmit,
  handleEdit,
  handleOptionChange,
  handleCancel,
  scheduleData,
  dateId,
  type,
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
    handleEdit,
    handleOptionChange,
    handleCancel,
    dateId,
    type,
  }
  return <ElementEditorView {...props} />
}

export default ElementEditor
