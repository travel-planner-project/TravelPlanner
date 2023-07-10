import { useState } from 'react'
import DropDown from './Dropdown'
import styles from './Time.module.scss'

type TimeProps = {
  dropDownOptions: string[]
}

function Time({ dropDownOptions }: TimeProps) {
  const [time, setTime] = useState<string>('00')
  const [showDropdown, setShowDropdown] = useState<boolean>(false)

  const handleDropdown = (action: 'open' | 'close') => {
    setShowDropdown(action === 'open')
  }

  return (
    <div className={styles.container}>
      <button
        type='button'
        className={showDropdown ? styles.showDropdown : styles.timeBtn}
        onClick={() => handleDropdown('open')}
      >
        {time}
      </button>
      {showDropdown && (
        <DropDown
          handleOption={setTime}
          options={dropDownOptions}
          selectedOption={time}
          closeDropdown={() => handleDropdown('close')}
        />
      )}
    </div>
  )
}

export default Time
