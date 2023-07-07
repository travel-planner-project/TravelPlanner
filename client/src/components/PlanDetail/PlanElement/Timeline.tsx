import { useState } from 'react'
import DropDown from './Dropdown'
import Icon from '../../Common/Icon'
import styles from './Timeline.module.scss'

const Time = Array.from({ length: 24 }, (_, idx) => String(idx).padStart(2, '0'))
const Minute = Array.from({ length: 6 }, (_, idx) => String(idx * 10).padStart(2, '0'))

function Timeline() {
  const [time, setTime] = useState<string>('00')
  const [minute, setMinute] = useState<string>('00')

  return (
    <div className={styles.container}>
      <div className={styles.timeDropdownBox}>
        {time}
        <DropDown handleOption={setTime} options={Time} selectedOption={time} />
      </div>
      :
      <div className={styles.timeDropdownBox}>
        {minute}
        <DropDown handleOption={setMinute} options={Minute} selectedOption={minute} />
      </div>
      <input type='text' />
      <Icon name='minus-square' />
    </div>
  )
}

export default Timeline
