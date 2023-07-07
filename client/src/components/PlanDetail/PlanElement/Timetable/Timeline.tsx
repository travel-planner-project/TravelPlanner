import { useState } from 'react'
import DropDown from './Dropdown'
import Icon from '../../../Common/Icon'
import styles from './Timeline.module.scss'

const TimeOptions = Array.from({ length: 24 }, (_, idx) => String(idx).padStart(2, '0'))
const MinuteOptions = Array.from({ length: 6 }, (_, idx) => String(idx * 10).padStart(2, '0'))

function Timeline() {
  const [showDropdown, setShowDropdown] = useState<{ time: boolean; minute: boolean }>({
    time: false,
    minute: false,
  })
  const [time, setTime] = useState<string>('00')
  const [minute, setMinute] = useState<string>('00')

  const handleShowDropdown = (type: 'time' | 'minute') => {
    setShowDropdown(pre => {
      return { ...pre, [type]: !pre[type] }
    })
  }

  return (
    <div className={styles.container}>
      <div className={styles.timeDropdownBox}>
        <button
          type='button'
          className={showDropdown.time ? styles.showDropdown : styles.timeBtn}
          onClick={() => handleShowDropdown('time')}
        >
          {time}
        </button>
        {showDropdown.time && (
          <DropDown handleOption={setTime} options={TimeOptions} selectedOption={time} />
        )}
      </div>
      :
      <div className={styles.timeDropdownBox}>
        <button
          type='button'
          className={showDropdown.minute ? styles.showDropdown : styles.timeBtn}
          onClick={() => handleShowDropdown('minute')}
        >
          {minute}
        </button>
        {showDropdown.minute && (
          <DropDown handleOption={setMinute} options={MinuteOptions} selectedOption={minute} />
        )}
      </div>
      <input type='text' className={styles.input} />
      <button type='button'>
        <Icon name='minus-square' />
      </button>
    </div>
  )
}

export default Timeline
