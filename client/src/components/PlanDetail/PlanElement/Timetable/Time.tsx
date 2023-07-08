import { useState } from 'react'
import DropDown from './Dropdown'
import styles from './Time.module.scss'

type TimeProps = {
  dropDownOptions: string[]
}

function Time({ dropDownOptions }: TimeProps) {
  const [time, setTime] = useState<string>('00')
  const [showDropdown, setShowDropdown] = useState<boolean>(false)

  const handleShowDropdown = (action: 'open' | 'close') => {
    setShowDropdown(action === 'open')
  }

  return (
    <div className={styles.container}>
      <button
        type='button'
        className={showDropdown ? styles.showDropdown : styles.timeBtn}
        onClick={() => handleShowDropdown('open')}
      >
        {time}
      </button>
      {showDropdown && (
        <DropDown
          handleOption={setTime}
          options={dropDownOptions}
          selectedOption={time}
          closeDropdown={() => handleShowDropdown('close')}
        />
      )}
    </div>
  )
}

export default Time
